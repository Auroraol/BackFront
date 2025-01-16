package aixiya.framework.backend.platform.foundation.api.utils.validator;


import aixiya.framework.backend.platform.foundation.api.annotation.SourceTypeValid;
import aixiya.framework.backend.platform.foundation.api.model.UploadVo;
import aixiya.framework.backend.platform.foundation.api.utils.constants.StorageApiConstants;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author wangqun865@163.com
 */
public class SourceTypeValidator implements ConstraintValidator<SourceTypeValid, UploadVo> {
    @Override
    public boolean isValid(UploadVo value, ConstraintValidatorContext context) {
        String sourceType = value.getSourceType();
        if (StringUtils.isEmpty(sourceType)) {
            return false;
        }
        if(!sourceType.equals(StorageApiConstants.SOURCE_TPYE_PUBLIC) &&
                !sourceType.equals(StorageApiConstants.SOURCE_TPYE_CLIENT) &&
                !sourceType.equals(StorageApiConstants.SOURCE_TPYE_CENTER) &&
                !sourceType.equals(StorageApiConstants.SOURCE_TPYE_BOTH) &&
                !sourceType.equals(StorageApiConstants.SOURCE_TPYE_NEITHER)) {
            return false;
        }

        if (sourceType.equals(StorageApiConstants.SOURCE_TPYE_CLIENT) && StringUtils.isEmpty(value.getClientId())) {
            return false;
        }
        if (sourceType.equals(StorageApiConstants.SOURCE_TPYE_CENTER) && StringUtils.isEmpty(value.getCenterId())) {
            return false;
        }
        if (sourceType.equals(StorageApiConstants.SOURCE_TPYE_BOTH)) {
            if (StringUtils.isEmpty(value.getClientId()) || StringUtils.isEmpty(value.getCenterId())) {
                return false;
            }
        }
        return true;
    }
}
