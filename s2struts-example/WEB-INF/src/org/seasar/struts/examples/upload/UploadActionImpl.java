/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.struts.examples.upload;

public class UploadActionImpl implements UploadAction {
    
    private UploadDto uploadDto;
    
    private String theText;
    
    public String execute() {
        uploadDto.setSize(uploadDto.getTheFile().getFileSize());
        System.out.println("set theText:" + theText);
        return SUCCESS;
    }

    public UploadDto getUploadDto() {
        return uploadDto;
    }

    public void setUploadDto(UploadDto uploadDto) {
        this.uploadDto = uploadDto;
    }
    
    public String getTheText() {
        return theText;
    }

    public void setTheText(String theText) {
        this.theText = theText;
    }

}
