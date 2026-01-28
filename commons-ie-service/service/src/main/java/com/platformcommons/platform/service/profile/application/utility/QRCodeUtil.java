package com.platformcommons.platform.service.profile.application.utility;

public interface QRCodeUtil {


    String getQRCodeLink(String data, Long entityId, String entityType,
                         String attachmentKind, String attachmentKindIdentifier, Boolean isPublic);
}
