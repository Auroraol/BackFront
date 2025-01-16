package aixiya.framework.backend.platform.foundation.service.impl.storage;

import aixiya.framework.backend.platform.foundation.api.common.exception.OcrDownloadException;
import aixiya.framework.backend.platform.foundation.api.model.DownloadUrlVo;
import aixiya.framework.backend.platform.foundation.api.model.DownloadVo;
import aixiya.framework.backend.platform.foundation.api.model.UploadVo;
import aixiya.framework.backend.platform.foundation.api.utils.constants.StorageApiConstants;
import aixiya.framework.backend.platform.foundation.common.constants.HuaweiObsConstants;
import aixiya.framework.backend.platform.foundation.common.constants.StorageConstants;
import aixiya.framework.backend.platform.foundation.entity.FileStorage;
import aixiya.framework.backend.platform.foundation.mapper.FileStorageMapper;
import aixiya.framework.backend.platform.foundation.service.ObsService;
import com.aixiya.framework.backend.common.exception.AixiyaFwRuntimeException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.obs.services.model.ObsObject;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Author wangqun865@163.com
 */
@Service
@Slf4j
public class ObsServiceImpl extends ServiceImpl<FileStorageMapper, FileStorage> implements ObsService {

    @Autowired
    private HuaweiObsFactory huaweiObsFactory;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DownloadVo> SimpleUpload(UploadVo uploadVo) {
        List<FileStorage> fileStorageList = huaweiObsFactory.buildPlatformStorageService().SimpleUpload(uploadVo);
        List<DownloadVo> downloadVoList = new ArrayList<>();
        fileStorageList.forEach(fileStorage -> {
            //fileStorage.setUpUserId(AixiyaFwUtil.getCurrentUser().getDid().toString());
            //fileStorage.setUpUserName(AixiyaFwUtil.getCurrentUser().getUsername());
            fileStorage.setUpUserId(uploadVo.getUpUserId());
            fileStorage.setUpUserName(uploadVo.getUpUserName());
            if (!StringUtils.isEmpty(uploadVo.getBusinessId())) {
                //如果有业务ID，则认为是已确认
                fileStorage.setConfirmDate(new Date());
                fileStorage.setConfirmUserId(fileStorage.getUpUserId());
                fileStorage.setConfirmUserName(fileStorage.getUpUserName());
            }
            this.baseMapper.insert(fileStorage);
            downloadVoList.add(convertDownVo(fileStorage));
        });
        return downloadVoList;
    }


    @Override
    public void simpleDownloadFile(HttpServletResponse response, String id, String withStyle) {
        FileStorage fileStorage = this.baseMapper.selectById(id);
        if (fileStorage == null) {
            return;
        }
        /*if ("1".equals(fileStorage.getDelFlag())) {
            return;
        }*/
        ObsObject obsObject = huaweiObsFactory.buildPlatformStorageService().getObject(fileStorage.getId() + fileStorage.getSuffix(), "1".equals(fileStorage.getAnonymous()), withStyle);
        InputStream input = obsObject.getObjectContent();
        byte[] b = new byte[1024];
        int len;
        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileStorage.getName(), "UTF-8") + "\"");
            if (!StringUtils.isEmpty(withStyle)) {
                response.setContentType("application/octet-stream");
            }
            while ((len = input.read(b)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
        } catch (IOException e) {
            log.error("ObsServiceImpl--> SimpleDownloadFile-->o :");
            log.error(e.getMessage() , e);
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                log.error("ObsServiceImpl-->SimpleDownloadFile-->1:");
                log.error(e.getMessage(),e);

            }
        }
    }

    @Override
    public byte[] downloadFileForOcr(String id) {
        FileStorage fileStorage = this.baseMapper.selectById(id);
        if (fileStorage == null) {
            throw new OcrDownloadException(String.format("文件id[%s]不存在", id));
        }
        if ("1".equals(fileStorage.getDelFlag())) {
            throw new OcrDownloadException(String.format("文件id[%s]不存在", id));
        }
        ObsObject obsObject = huaweiObsFactory.buildPlatformStorageService().getObject(fileStorage.getId() + fileStorage.getSuffix(), "1".equals(fileStorage.getAnonymous()), null);
        InputStream input = obsObject.getObjectContent();
        byte[] response;
        try {
            response = toByteArray(input);
        } catch (IOException e) {
            log.error("download file ocr for io parse error");
            log.error(e.getMessage(),e);
            throw new OcrDownloadException("download file ocr for io parse error");
        }
        return response;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n = 0;
        //while (-1 != (n = input.read(buffer))) {
        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
        input.close();
        byte[] res = output.toByteArray();
        output.flush();
        output.close();

        //return output.toByteArray();
        return res;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void simpleConfirm(List<DownloadVo> downloadVoList) {
        HashMap<String, ArrayList<String>> delMap = new HashMap<String, ArrayList<String>>();

        for (DownloadVo downloadVo : downloadVoList) {
            FileStorage fileStorage = this.baseMapper.selectById(downloadVo.getId());
            if (fileStorage == null) {
                throw new AixiyaFwRuntimeException(String.format("文件id[%s]不存在", downloadVo.getId()));
            }
            boolean flag = havePermission(fileStorage , downloadVo);
            if (!flag) {
                throw new AixiyaFwRuntimeException(String.format("文件id[%s]无提交权限，请联系管理员" , downloadVo.getId()));
            }

            ArrayList<String> fileTypes = null;
            if (!delMap.containsKey(downloadVo.getBusinessId())) {
                fileTypes = new ArrayList<>();
                delMap.put(downloadVo.getBusinessId(), fileTypes);
            } else {
                fileTypes = delMap.get(downloadVo.getBusinessId());
            }
            if (!fileTypes.contains(fileStorage.getType())) {
                fileTypes.add(fileStorage.getType());
            }
        }

        for (Map.Entry<String, ArrayList<String>> entry : delMap.entrySet()) {
            UpdateWrapper<FileStorage> wrapper = new UpdateWrapper<>();
            wrapper.eq("business_id",entry.getKey()).in("type" , entry.getValue());

            FileStorage fileStorage = new FileStorage();
            fileStorage.setDelFlag(StorageConstants.DEL_TRUE);
            this.baseMapper.update(fileStorage,wrapper);
        }

        Date confirmDate = new Date();
        for (DownloadVo downloadVo : downloadVoList) {
            FileStorage fileStorage = new FileStorage();
            fileStorage.setId(downloadVo.getId());
            fileStorage.setBusinessId(downloadVo.getBusinessId());
            fileStorage.setConfirmDate(confirmDate);
           /* CurrentUser u = AixiyaFwUtil.getCurrentUser();
            fileStorage.setConfirmUserId(AixiyaFwUtil.getCurrentUser().getDid().toString());
            fileStorage.setConfirmUserName(AixiyaFwUtil.getCurrentUser().getUsername());*/
            fileStorage.setConfirmUserId(downloadVo.getConfirmUserId());
            fileStorage.setConfirmUserName(downloadVo.getConfirmUserName());
            fileStorage.setDelFlag(StorageConstants.DEL_FALSE);
            fileStorage.setSystemName(downloadVo.getSystemName());
            fileStorage.setSystemName(downloadVo.getSystemName());
            this.baseMapper.updateById(fileStorage);
        }

    }

    @Override
    public List<DownloadVo> downloadUrls(DownloadUrlVo downloadUrlVo) {
        QueryWrapper<FileStorage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_id" ,downloadUrlVo.getBusinessId()).eq("del_flag" , StorageConstants.DEL_FALSE).eq("source_type" ,downloadUrlVo.getSourceType());
        if (downloadUrlVo.getTypeList() != null && downloadUrlVo.getTypeList().size() > 0) {
            queryWrapper.and(wrapper ->wrapper.in("type" ,downloadUrlVo.getTypeList()));
        }
        if (downloadUrlVo.getSourceType().equals(StorageApiConstants.SOURCE_TPYE_CLIENT)) {
            queryWrapper.and(wrapper -> wrapper.eq("client_id" , downloadUrlVo.getClientId()));
        }
        if (downloadUrlVo.getSourceType().equals(StorageApiConstants.SOURCE_TPYE_CENTER)) {
            queryWrapper.and(wrapper -> wrapper.eq("center_id" , downloadUrlVo.getCenterId()));
        }
        if (downloadUrlVo.getSourceType().equals(StorageApiConstants.SOURCE_TPYE_BOTH)) {
            //todo 测试这种写法应该也行
            queryWrapper.eq("client_id" , downloadUrlVo.getClientId()).eq("center_id" , downloadUrlVo.getCenterId());
        }
        List<FileStorage> fileStorageList = this.baseMapper.selectList(queryWrapper);
        List<DownloadVo> downloadVoList = new ArrayList<>();
        fileStorageList.forEach(fileStorage-> {
            downloadVoList.add(convertDownVo(fileStorage));
        });
        return downloadVoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(String fileId, boolean deep , boolean isAnonymous) {
        if (StringUtils.isEmpty(fileId)) {
            return;
        }
        FileStorage fileStorage = new FileStorage();
        fileStorage.setId(fileId);
        fileStorage.setDelFlag(StorageConstants.DEL_TRUE);
        this.baseMapper.updateById(fileStorage);

        if (deep) {
            huaweiObsFactory.buildPlatformStorageService().deleteObject(fileId , isAnonymous);
        }
    }


    private boolean havePermission(FileStorage fileStorage ,DownloadVo downloadVo ) {
        String sourceType = fileStorage.getSourceType();
        String clientId = downloadVo.getClientId();
        String centerId = downloadVo.getCenterId();
        if (StringUtils.isEmpty(sourceType)) {
            return true;
        } else if (sourceType.equals(StorageApiConstants.SOURCE_TPYE_PUBLIC)) {
            return true;
        } else if (sourceType.equals(StorageApiConstants.SOURCE_TPYE_CLIENT)) {
            if (!StringUtils.isEmpty(clientId) &&  clientId.equals(fileStorage.getClientId())) {
                return true;
            }
        } else if (sourceType.equals(StorageApiConstants.SOURCE_TPYE_CENTER)) {
            if (!StringUtils.isEmpty(centerId) && centerId.equals(fileStorage.getCenterId())) {
                return true;
            }
        } else if (sourceType.equals(StorageApiConstants.SOURCE_TPYE_BOTH)) {
            if (!StringUtils.isEmpty(centerId) && centerId.equals(fileStorage.getCenterId()) &&
                    !StringUtils.isEmpty(clientId) &&  clientId.equals(fileStorage.getClientId())) {
                return true;
            }
        } else if (sourceType.equals(StorageApiConstants.SOURCE_TPYE_NEITHER)) {
            return true;
        }
        return false;
    }

    private String getDownFileUrl(String id) {
        return "/public/storage/download/file/" + id;
    }

    private String getPreviewUrl(String id) {
        return "/public/storage/download/pre/" + id;
    }

    private DownloadVo convertDownVo(FileStorage fileStorage) {
        DownloadVo downloadVo = new DownloadVo();
        if (fileStorage != null) {
            downloadVo.setBusinessId(fileStorage.getBusinessId());
            downloadVo.setClientId(fileStorage.getClientId());
            if (fileStorage.getConfirmDate() != null) {
                downloadVo.setConfirmDate(DateFormatUtils.format(fileStorage.getConfirmDate(), "yyyy-MM-dd HH:mm:ss"));
            }
            if (fileStorage.getUpDate() != null) {
                downloadVo.setUpDate(DateFormatUtils.format(fileStorage.getUpDate(), "yyyy-MM-dd HH:mm:ss"));
            }
            downloadVo.setConfirmUserId(fileStorage.getConfirmUserId());
            downloadVo.setConfirmUserName(fileStorage.getConfirmUserName());
            downloadVo.setId(fileStorage.getId());
            downloadVo.setName(fileStorage.getName());
            downloadVo.setPrefix(fileStorage.getPrefix());
            downloadVo.setRemark(fileStorage.getRemark());
            downloadVo.setSuffix(fileStorage.getSuffix());
            downloadVo.setSystemName(fileStorage.getSystemName());
            downloadVo.setType(fileStorage.getType());
            // todo 文件类型名称 应是存在码表里的，这部分用户中心和业务系统统一处理？
            downloadVo.setTypeName(fileStorage.getTypeName());
            downloadVo.setUpUserId(fileStorage.getUpUserId());
            downloadVo.setUpUserName(fileStorage.getUpUserName());
            downloadVo.setDownloadUrl(getDownFileUrl(fileStorage.getId()));
            //根据文件后缀判读是否是图片
            if (Arrays.stream(HuaweiObsConstants.IMG_TYPES).anyMatch(f -> f.equals(downloadVo.getSuffix().toLowerCase()))) {
                downloadVo.setImgStyleUrl(getPreviewUrl(fileStorage.getId()));
            } else {
                downloadVo.setImgStyleUrl(downloadVo.getDownloadUrl());
            }
            downloadVo.setSourceType(fileStorage.getSourceType());
            downloadVo.setCenterId(fileStorage.getCenterId());
        }
        return downloadVo;
    }
}
