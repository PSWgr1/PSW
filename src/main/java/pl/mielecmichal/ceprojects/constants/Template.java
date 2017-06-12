package pl.mielecmichal.ceprojects.constants;

public enum Template {

    INTRO("/contents/introContent.vm"),
    PROJECTS("/contents/projectsContent.vm"),
    PROJECT_DETAILS("/contents/projectDetailsContent.vm");

    private final String path;

    public String getPath() {
        return path;
    }

    Template(String path) {
        this.path = path;
    }
}
