package webmvc.org.springframework.web.servlet.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webmvc.org.springframework.web.servlet.View;

public class JspView implements View {

    private static final Logger log = LoggerFactory.getLogger(JspView.class);

    public static final String REDIRECT_PREFIX = "redirect:";
    private final String viewName;

    private JspView(final String viewName) {
        this.viewName = viewName;
    }

    public static JspView of(final String viewName) {
        return new JspView(viewName);
    }

    public static JspView redirect(final String viewName) {
        return new JspView(REDIRECT_PREFIX + viewName);
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        if (viewName.startsWith(JspView.REDIRECT_PREFIX)) {
            response.sendRedirect(viewName.substring(JspView.REDIRECT_PREFIX.length()));
            return;
        }

        model.keySet().forEach(key -> {
            log.debug("attribute name : {}, value : {}", key, model.get(key));
            request.setAttribute(key, model.get(key));
        });

        final var requestDispatcher = request.getRequestDispatcher(viewName);
        requestDispatcher.forward(request, response);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JspView jspView = (JspView) o;
        return Objects.equals(viewName, jspView.viewName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(viewName);
    }
}
