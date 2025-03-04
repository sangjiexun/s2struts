package org.seasar.struts.examples.web.upload;

import org.seasar.struts.annotation.tiger.StrutsActionForward;

abstract class AbstractAction {

    @StrutsActionForward
    public static final String UPLOAD = "/WEB-INF/view/upload/upload.jsp";

    @StrutsActionForward
    public static final String RESULT = "/WEB-INF/view/upload/result.jsp";
}
