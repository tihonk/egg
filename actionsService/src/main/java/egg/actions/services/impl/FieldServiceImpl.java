package egg.actions.services.impl;

import egg.actions.repository.FieldRepository;
import egg.actions.repository.UserRepository;
import egg.actions.services.FieldService;
import egg.actions.services.field.FightService;
import egg.actions.services.field.LikeFieldService;
import egg.actions.services.field.RealtyService;
import egg.models.mainModels.FieldModel;
import egg.models.mainModels.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("fieldService")
public class FieldServiceImpl implements FieldService {

    private final RealtyService realtyService;
    private final FightService fightService;
    private final LikeFieldService likeFieldService;

    private final FieldRepository fieldRepository;
    private final UserRepository userRepository;

    @Autowired
    public FieldServiceImpl(@Qualifier("realtyService") RealtyService realtyService,
                            @Qualifier("fightService") FightService fightService,
                            @Qualifier("likeFieldService") LikeFieldService likeFieldService,
                            @Qualifier("fieldRepository") FieldRepository fieldRepository,
                            @Qualifier("userRepository") UserRepository userRepository){
        super();
        this.realtyService = realtyService;
        this.fightService = fightService;
        this.likeFieldService = likeFieldService;
        this.fieldRepository = fieldRepository;
        this.userRepository = userRepository;
    }

    public void giveFreeField(Long userId) {
        UserModel user = getUserById(userId);
        realtyService.giveFreeField(user);
    }

    public void buyTheField(Long buyerUserId, Long boughtFieldId) {
        UserModel buyerUser = getUserById(buyerUserId);
        FieldModel boughtField = getFieldById(boughtFieldId);
        realtyService.buyTheField(buyerUser, boughtField);
    }

    public void attackTheField(Long attackingFieldId, Long attackedFieldId) {
        FieldModel attackingField= getFieldById(attackingFieldId);
        FieldModel attackedField = getFieldById(attackedFieldId);
        fightService.attack(attackingField, attackedField);
    }

    public void fillInTheField() {
    }

    public void likeTheField(Long likedUserId, Long likedFieldId) {
        UserModel likedUser = getUserById(likedUserId);
        FieldModel likedField = getFieldById(likedFieldId);
        likeFieldService.like(likedUser, likedField);
    }

    private UserModel getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    private FieldModel getFieldById(Long fieldId) {
        return fieldRepository.findById(fieldId);
    }
}
