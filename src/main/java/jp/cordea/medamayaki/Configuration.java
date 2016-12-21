package jp.cordea.medamayaki;

import hudson.Extension;
import hudson.util.ListBoxModel;
import jenkins.model.GlobalConfiguration;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Created by Yoshihiro Tanaka on 2016/10/10.
 */
@Extension
public class Configuration extends GlobalConfiguration {

    @Getter
    @Setter
    private String style;

    public Configuration() {
        load();
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
        req.bindJSON(this, json);
        save();

        return super.configure(req, json);
    }

    static Configuration get() {
        return GlobalConfiguration.all().get(Configuration.class);
    }

    public ListBoxModel doFillStyleItems() {
        ListBoxModel items = new ListBoxModel();
        items.add("Sunny-side up");
        items.add("Over easy");
        items.add("Over hard");
        return items;
    }

}
