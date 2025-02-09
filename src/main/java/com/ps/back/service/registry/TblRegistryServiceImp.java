package com.ps.back.service.registry;

import com.ps.back.dao.tblRegistry.TblRegistryDao;
import com.ps.back.dao.tbluser.TblUserDao;
import com.ps.back.model.entity.TblRegistry;
import com.ps.back.model.entity.TblRol;
import com.ps.back.model.entity.TblUser;
import com.ps.back.model.entity.enums.ParkingStateEnum;
import com.ps.back.model.entity.enums.RoleEnum;
import com.ps.back.model.exception.ResourceNotFoundException;
import com.ps.back.model.pojos.consume.ConsumeJsonLong;
import com.ps.back.model.pojos.response.ResponseJsonRegistry;
import com.ps.back.service.util.TimeUtils;
import com.ps.back.service.util.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TblRegistryServiceImp implements TblRegistryService {

    private final TblRegistryDao tblRegistryDao;
    private final TblUserDao tblUserDao;

    //*TODO hacer el metodo para salir y registrar total y hora de salida*/
    @Override
    public ResponseJsonRegistry createOrUpdateRegistryByCveUser(ConsumeJsonLong consume){
        ValidUtils.validateConsume(consume);
        ResponseJsonRegistry response = new ResponseJsonRegistry();
        long cveUser = consume.getId();
        if (!tblUserDao.existsTblUserByCveUser(cveUser)){
            throw new ResourceNotFoundException("User not found with id " + consume.getId());
        }
        TblUser user = tblUserDao.findTblUserByCveUser(cveUser);
        TblRegistry registry = tblRegistryDao.findTblRegistryByCveUser(cveUser) == null ?
                new TblRegistry() : tblRegistryDao.findTblRegistryByCveUser(cveUser);


        if(registry.getParkingState() == null){
            registry.setParkingState(ParkingStateEnum.NOT_PARKED);
        }

        int userType =  (int) validateUserType(user);

        return addOrRemoveRegistry(userType,registry,user);
    }

    private ResponseJsonRegistry addOrRemoveRegistry(int userType, TblRegistry registry, TblUser user){
        switch (userType){
            case 1: break;
            case 2: break;
            case 3:
                return  registry.getParkingState().equals(ParkingStateEnum.NOT_PARKED) ?
                        buildResponseJsonRegistry(addGenericRegistry(user)):
                        buildResponseJsonRegistry(removeGenericRegistry(user));


            default: throw new ResourceNotFoundException("unexpected user type " + userType);
        }
        return null;
    }

    private ResponseJsonRegistry buildResponseJsonRegistry(TblRegistry tblRegistry){
        String exitDate =tblRegistry.getExitdate() == null ? "not available" :
                TimeUtils.getDateFormated(tblRegistry.getExitdate(),null);
        BigDecimal total = tblRegistry.getTotal() == null ? BigDecimal.ZERO :
                tblRegistry.getTotal();
        return ResponseJsonRegistry.builder()
                .cveRegistry(tblRegistry.getCveReg())
                .entryDate(TimeUtils.getDateFormated(tblRegistry.getEntrydate(),null))
                .exitDate(exitDate)
                .parkingState(tblRegistry.getParkingState().toString())
                .total(total)
                .build();
    }

    private TblRegistry addGenericRegistry(TblUser user) {
        TblRegistry registry = new TblRegistry();
        return createOrUpdateRegistry(TblRegistry.builder()
                .entrydate(TimeUtils.getCurrentTime())
                .parkingState(ParkingStateEnum.PARKED)
                .build());
    }

    private TblRegistry removeGenericRegistry(TblUser user) {
        LocalDateTime exitDate = TimeUtils.getCurrentTime();
        LocalDateTime entryDate = TimeUtils.getCurrentTime();
        return createOrUpdateRegistry(TblRegistry.builder()
                .exitdate(exitDate)
                .parkingState(ParkingStateEnum.NOT_PARKED)
                .total(getTotalByTimeAndType(entryDate,exitDate,3))
                .build());
    }

    private TblRegistry createOrUpdateRegistry(TblRegistry tblRegistry) {

        ValidUtils.validateConsume(tblRegistry);
        long cveReg = tblRegistry.getCveReg() == null ? 0 : tblRegistry.getCveReg();

        if (cveReg > 0){
            if (!tblRegistryDao.existTblRegistryByCveReg(cveReg)){
                throw new ResourceNotFoundException("Unable to find registry with id " + cveReg);
            }
            tblRegistry = tblRegistryDao.findTblRegistryByCveReg(cveReg);
        }

        return tblRegistryDao.createOrUpdateTblRegistry(tblRegistry);
    }

    private long validateUserType(TblUser user) {
        ValidUtils.validateConsume(user);
        ValidUtils.validateConsume(user.getCveUser());

        return user.getTblRoles().stream()
                .filter(role -> !role.getNameRol().equals(RoleEnum.USER))
                .map(TblRol::getCveRol)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not valid with type: "
                        + user.getNameUser()));
    }


    private BigDecimal getTotalByTimeAndType(LocalDateTime entryDate, LocalDateTime exitDate, int typeUser) {
        Duration duration = Duration.between(entryDate, exitDate);
        long hours = duration.toHours();
        double price = switch (typeUser) {
            case 1 -> RoleEnum.ADMIN.getPrice();
            case 2 -> RoleEnum.USER.getPrice();
            case 3 -> RoleEnum.GENERIC.getPrice();
            case 4 -> RoleEnum.STORAGE.getPrice();
            default -> throw new IllegalArgumentException("Invalid type user");
        };
        return new BigDecimal(hours * price);
    }
}
