package com.tuean.consts;

public enum ResourceType {

    json,

    html,

    javascript,

    css,

    md,

    ;

    public static ResourceType getByExtension(String extension) {
        switch (extension) {
            case "json": return json;
            case "html":
            case "htm":
                return html;
            case "js": return javascript;
            case "markdown": return md;
            case "css":
            case "sass":
            case "less":
                return css;
        }
        return ResourceType.html;
    }

}
