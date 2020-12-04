package Data;

import Data.Interface.DoctorTab;
import Data.Interface.PatientTab;
import Data.Interface.RecipeTab;
import static Data.SQLCommands.checkprior;
import static Data.SQLCommands.createTables;
import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
/**

 * @author Denisov Ivan
 */
@Theme("mytheme")
public class MyUI extends UI {

        @Override
        protected  void init(VaadinRequest vaadinRequest) {
                createTables();
                checkprior();
                TabSheet tabsheet = new TabSheet();
                tabsheet.setSizeFull();
                PatientTab PT = new PatientTab();
                DoctorTab DT = new DoctorTab();
                RecipeTab RT = new RecipeTab();
                tabsheet.addTab(PT.getMainPatientPane(), "Patient menu");
                tabsheet.addTab(DT.getMainDoctorPane(), "Doctor menu");    
                tabsheet.addTab(RT.getMainRecipePane(), "Recipe menu");
                setContent(tabsheet);
        }

        @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
        @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
        public static class MyUIServlet extends VaadinServlet {
        }
}
