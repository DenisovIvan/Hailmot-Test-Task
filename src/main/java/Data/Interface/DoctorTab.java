
package Data.Interface;

import Data.DAO.DoctorDAO;
import Data.DAO.RecipeDAO;
import Data.Things.Doctor;
import Data.Things.Recipe;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.SingleSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class DoctorTab {

        private final Grid<Doctor> gridDoctor = new Grid<>(Doctor.class);
        private final Grid<Doctor> gridDoctorStatistic = new Grid<>();
        private final Window addDoctorWindow = new Window("Add a new Doctor");
        private final Window updateDoctorWindow = new Window("Update a Doctor");
        private final Window doctorStatisticWindow = new Window("Doctor Statistic");
        private final TextField addName = new TextField("Insert Name");
        private final TextField addSurname = new TextField("Insert Surname");
        private final TextField addPatronymic = new TextField("Insert Patronymic");
        private final TextField addSpecialization = new TextField("Insert Specialization");
        private final TextField updateName = new TextField("Insert Name");
        private final TextField updateSurname = new TextField("Insert Surname");
        private final TextField updatePatronymic = new TextField("Insert Patronymic");
        private final TextField updateSpecialization = new TextField("Insert Specialization");
        private final VerticalLayout subContentAdd = new VerticalLayout();
        private final VerticalLayout subContentUpdate = new VerticalLayout();
        private final HorizontalLayout HlayoutDoctor = new HorizontalLayout();
        private final HorizontalLayout HlayoutAdd = new HorizontalLayout();
        private final HorizontalLayout HlayoutUpdate = new HorizontalLayout();
        private final Panel doctorspanel = new Panel("List of Doctor");
        private final Button statistic = new Button("View Statistic");
        private final Button update = new Button("Update Doctor");
        private final Button delete = new Button("Delete  Doctor");
        private final Button add = new Button("Add new Doctor");
        private final Button addingAcceptButton = new Button("Ok     ");
        private final Button addingDeclineButton = new Button("Decline");
        private final Button updateAcceptButton = new Button("Ok     ");
        private final Button updateDeclineButton = new Button("Decline");
        private final VerticalLayout dbl = new VerticalLayout(statistic, add, update, delete);
        private final Panel doctorbuttons = new Panel(dbl);
        private DoctorDAO docdao = new DoctorDAO();
        List<Doctor> doclist = new ArrayList<Doctor>();
        private final String regex = "\\w+";
        private final String regexDigit = "\\d+";
        List<Recipe> reclist = new ArrayList<Recipe>();
        private RecipeDAO recdao = new RecipeDAO();

        public HorizontalLayout getMainDoctorPane() {
                AddButtonBinding();
                DocAddWinSetting();
                UpdateButtonBinding();
                DocUpdateWinSetting();
                DeleteButtonBinding();
                DocStatisticSetting();
                statisticGridSetting();
                StatisticButtonBinding();
                gridDoctor.setWidth(1000.0f, Unit.PIXELS);
                gridDoctor.setColumnOrder("id", "name", "surname", "patronymic", "specialization");
                gridDoctor.setSelectionMode(Grid.SelectionMode.SINGLE);
                gridDoctor.removeColumn("statistic");
                gridDoctor.removeColumn("infoStatistic");
                updateDocGrid();
                doctorspanel.setContent(gridDoctor);
                HlayoutDoctor.addComponent(doctorspanel);
                HlayoutDoctor.addComponent(doctorbuttons);
                return HlayoutDoctor;
        }

        private void DocAddWinSetting() {
                HlayoutAdd.addComponents(addingAcceptButton, addingDeclineButton);
                subContentAdd.addComponents(addName, addSurname, addPatronymic, addSpecialization, HlayoutAdd);
                addDoctorWindow.setContent(subContentAdd);
                addDoctorWindow.setWidth(220.0f, Unit.PIXELS);
                addDoctorWindow.center();
                addDoctorWindow.setModal(true);
        }

        private void DocUpdateWinSetting() {
                HlayoutUpdate.addComponents(updateAcceptButton, updateDeclineButton);
                subContentUpdate.addComponents(updateName, updateSurname, updatePatronymic, updateSpecialization, HlayoutUpdate);
                updateDoctorWindow.setContent(subContentUpdate);
                updateDoctorWindow.setWidth(220.0f, Unit.PIXELS);
                updateDoctorWindow.center();
                updateDoctorWindow.setModal(true);
        }

        private void DocStatisticSetting() {
                doctorStatisticWindow.setWidth(600.0f, Unit.PIXELS);
                doctorStatisticWindow.center();
                doctorStatisticWindow.setModal(true);
                doctorStatisticWindow.setContent(gridDoctorStatistic);
        }

        private void AddButtonBinding() {
                add.addClickListener(event -> {
                        if (!addDoctorWindow.isAttached()) {

                                UI.getCurrent().addWindow(addDoctorWindow);
                                Notification.show("Adding a new Doctor", Type.TRAY_NOTIFICATION);
                        }
                });
                addingDeclineButton.addClickListener(event -> {
                        addDoctorWindow.close();
                });
                addingAcceptButton.addClickListener(event -> {
                        boolean permition = true;
                        if (addName.getValue().matches(regexDigit) || !addName.getValue().matches(regex) || addName.getValue().length() > 30 || addName.getValue() == "") {
                                permition = false;
                                addName.setValue("incorrect input");
                        }
                        if (addSurname.getValue().matches(regexDigit) || !addSurname.getValue().matches(regex) || addSurname.getValue().length() > 30 || addSurname.getValue() == "") {
                                permition = false;
                                addSurname.setValue("incorrect input");
                        }
                        if (addPatronymic.getValue().matches(regexDigit) || !addPatronymic.getValue().matches(regex) || addPatronymic.getValue().length() > 30 || addPatronymic.getValue() == "") {
                                permition = false;
                                addPatronymic.setValue("incorrect input");
                        }
                        if (addSpecialization.getValue().length() > 30 || addSpecialization.getValue() == "") {
                                permition = false;
                                addSpecialization.setValue("incorrect input");
                        }
                        if (permition) {
                                Doctor doc = new Doctor(0, addName.getValue(), addSurname.getValue(), addPatronymic.getValue(), addSpecialization.getValue());
                                if (docdao.createInTableBySubject(doc)) {
                                        addDoctorWindow.close();
                                        Notification.show("Doctor " + addName.getValue() + " " + addSurname.getValue() + " " + addPatronymic.getValue() + " was added", Type.TRAY_NOTIFICATION);
                                        updateDocGrid();
                                } else {
                                        Notification.show("Some strange Error", Type.ERROR_MESSAGE);
                                }
                        } else {
                                Notification.show("Correct the fields", Type.ERROR_MESSAGE);
                        }
                });
        }

        private void UpdateButtonBinding() {
                update.addClickListener(event -> {
                        if (!updateDoctorWindow.isAttached()) {
                                SingleSelect<Doctor> Selection = gridDoctor.asSingleSelect();
                                if (Selection.getValue() != null) {
                                        Doctor doc = new Doctor();
                                        doc = Selection.getValue();
                                        updateName.setValue(doc.getName());
                                        updateSurname.setValue(doc.getSurname());
                                        updatePatronymic.setValue(doc.getPatronymic());
                                        updateSpecialization.setValue(doc.getSpecialization());
                                        UI.getCurrent().addWindow(updateDoctorWindow);
                                        Notification.show("Open doctor redactor", Type.TRAY_NOTIFICATION);
                                } else {
                                        Notification.show("choose a doctor in a table ", Type.ERROR_MESSAGE);
                                }
                        }
                });
                updateDeclineButton.addClickListener(event -> {
                        updateDoctorWindow.close();
                });
                updateAcceptButton.addClickListener(event -> {
                        boolean permition = true;
                        if (updateName.getValue().matches(regexDigit) || !updateName.getValue().matches(regex) || updateName.getValue().length() > 30 || updateName.getValue() == "") {
                                permition = false;
                                updateName.setValue("incorrect input");
                        }
                        if (updateSurname.getValue().matches(regexDigit) || !updateSurname.getValue().matches(regex) || updateSurname.getValue().length() > 30 || updateSurname.getValue() == "") {
                                permition = false;
                                updateSurname.setValue("incorrect input");
                        }
                        if (updatePatronymic.getValue().matches(regexDigit) || !updatePatronymic.getValue().matches(regex) || updatePatronymic.getValue().length() > 30 || updatePatronymic.getValue() == "") {
                                permition = false;
                                updatePatronymic.setValue("incorrect input");
                        }
                        if (updateSpecialization.getValue().length() > 30 || updateSpecialization.getValue() == "") {
                                permition = false;
                                updateSpecialization.setValue("incorrect input");
                        }
                        if (permition) {
                                Doctor doc = new Doctor();
                                SingleSelect<Doctor> Selection = gridDoctor.asSingleSelect();
                                doc = Selection.getValue();
                                if (Selection.getValue() == null) {

                                        Notification.show("Doctor disapeard", Type.ERROR_MESSAGE);
                                } else {
                                        doc.setName(updateName.getValue());
                                        doc.setSurname(updateSurname.getValue());
                                        doc.setPatronymic(updatePatronymic.getValue());
                                        doc.setSpecialization(updateSpecialization.getValue());
                                        if (docdao.updateInTableBySubject(doc)) {
                                                Notification.show("Doctor " + doc.getName() + " " + doc.getSurname() + " " + doc.getPatronymic() + " was updated", Type.TRAY_NOTIFICATION);
                                                updateDocGrid();
                                        } else {
                                                Notification.show("Some syntax Error", Type.ERROR_MESSAGE);
                                        }
                                }
                                updateDoctorWindow.close();
                        } else {
                                Notification.show("Correct the fields", Type.ERROR_MESSAGE);
                        }
                });
        }

        private void DeleteButtonBinding() {
                delete.addClickListener(event -> {
                        Doctor doc = new Doctor();
                        SingleSelect<Doctor> Selection = gridDoctor.asSingleSelect();
                        doc = Selection.getValue();
                        if (Selection.getValue() == null) {
                                Notification.show("choose a doctor in a table ", Type.ERROR_MESSAGE);
                        } else {
                                reclist.clear();
                                reclist = recdao.readToSubjectList();
                                boolean check = true;
                                for (int i = 0; i < reclist.size(); i++) {
                                        if (reclist.get(i).getDoctor().getId().equals(doc.getId())) {
                                                check = false;
                                                break;
                                        }
                                }
                                if (check) {
                                        if (docdao.deleteInTableBySubject(doc)) {
                                                Notification.show("Doctor " + doc.getName() + " " + doc.getSurname() + " " + doc.getPatronymic() + " was deleted", Type.TRAY_NOTIFICATION);
                                                updateDocGrid();
                                        } else {
                                                Notification.show("Some syntax error", Type.ERROR_MESSAGE);
                                        }
                                } else {
                                        Notification.show("Recipe with this Doctor is exist", Type.ERROR_MESSAGE);
                                }
                        }
                });
        }

        private void updateDocGrid() {
                doclist.clear();
                doclist = docdao.readToSubjectList();
                gridDoctor.setItems(doclist);
        }

        private void statisticGridSetting() {
                gridDoctorStatistic.addColumn(Doctor::getInfoStatistic).setCaption("Doctor");
                gridDoctorStatistic.addColumn(Doctor::getStatistic).setCaption("Doctor's prescriptions");
        }

        private void StatisticButtonBinding() {
                statistic.addClickListener(event -> {
                        if (!doctorStatisticWindow.isAttached()) {
                                for (int i = 0; i < doclist.size(); i++) {
                                        doclist.get(i).setStatistic(0);
                                }
                                reclist.clear();
                                reclist = recdao.readToSubjectList();
                                for (int i = 0; i < doclist.size(); i++) {
                                        for (int j = 0; j < reclist.size(); j++) {
                                                if (reclist.get(j).getDoctor().getId().equals(doclist.get(i).getId())) {
                                                        doclist.get(i).setStatistic(doclist.get(i).getStatistic() + 1);
                                                }
                                        }
                                }
                                gridDoctorStatistic.setItems(doclist);
                                UI.getCurrent().addWindow(doctorStatisticWindow);
                        }

                });

        }
}
