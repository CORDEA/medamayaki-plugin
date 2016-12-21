package jp.cordea.medamayaki;

import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.model.Result;
import hudson.util.FormValidation;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import hudson.util.ListBoxModel;
import jenkins.tasks.SimpleBuildStep;
import lombok.Getter;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;

/**
 * Created by Yoshihiro Tanaka on 2016/10/10.
 */
public class MedamayakiBuilder extends Builder implements SimpleBuildStep {

    @Getter
    private final int oil;

    @Getter
    private final String seasoning;

    public boolean getIsSalt() {
        return isSalt;
    }

    private final boolean isSalt;

    public boolean getIsBacon() {
        return isBacon;
    }

    private final boolean isBacon;

    @DataBoundConstructor
    public MedamayakiBuilder(String oil, boolean isBacon, boolean isSalt, String seasoning) {
        this.oil = Integer.parseInt(oil);
        this.isBacon = isBacon;
        this.isSalt = isSalt;
        this.seasoning = seasoning;
    }

    @Override
    public void perform(Run<?,?> build, FilePath workspace, Launcher launcher, TaskListener listener) {

        PrintStream logger = listener.getLogger();

        logger.println("Let me confirm your order.");

        logger.println("Style: " + Configuration.get().getStyle());
        logger.println("Olive oil: " + oil);
        logger.println("Salt: " + isSalt);
        logger.println("Bacon: " + isBacon);
        logger.println("Seasoning: " + seasoning);

        logger.println("Here’s your fried egg.");

        Random random = new Random();
        switch (random.nextInt(3)) {
            case 0:
                build.setResult(Result.SUCCESS);
                break;
            case 1:
                build.setResult(Result.FAILURE);
                break;
            case 2:
                build.setResult(Result.NOT_BUILT);
                break;
        }
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl)super.getDescriptor();
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public DescriptorImpl() {
            load();
        }

        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        public String getDisplayName() {
            return "Fry an egg";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            return super.configure(req,formData);
        }

        public FormValidation doCheckOil(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0) {
                return FormValidation.error("Please set the amount of olive oil.");
            }
            try {
                int ignore = Integer.parseInt(value);
            } catch (NumberFormatException ignore) {
                return FormValidation.error("Please set the amount of olive oil.");
            }
            return FormValidation.ok();
        }

        public ListBoxModel doFillSeasoningItems() {
            ListBoxModel items = new ListBoxModel();
            items.add("Soy sauce");
            items.add("Source");
            items.add("Black pepper");
            items.add("None");
            return items;
        }
    }
}

