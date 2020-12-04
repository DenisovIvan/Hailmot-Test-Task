package Data.Interface;

import Data.DAO.PatientDAO;
import Data.DAO.RecipeDAO;
import Data.Things.Patient;
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

public class PatientTab {

        private final Grid<Patient> gridPatient = new Grid<>(Patient.class);
        private final Window addPatientWindow = new Window("Add a new Patient");
        private final Window updatePatientWindow = new Window("Update a Patient");
        private final TextField addName = new TextField("Insert Name");
        private final TextField addSurname = new TextField("Insert Surname");
        private final TextField addPatronymic = new TextField("Insert Patronymic");
        private final TextField addTelephone = new TextField("Insert telephone");
        private final TextField updateName = new TextField("Insert Name");
        private final TextField updateSurname = new TextField("Insert Surname");
        private final TextField updatePatronymic = new TextField("Insert Patronymic");
        private final TextField updateTelephone = new TextField("Insert telephone");
        private final VerticalLayout subContentAdd = new VerticalLayout();
        private final VerticalLayout subContentUpdate = new VerticalLayout();
        private final HorizontalLayout HlayoutPatient = new HorizontalLayout();
        private final HorizontalLayout HlayoutAdd = new HorizontalLayout();
        private final HorizontalLayout HlayoutUpdate = new HorizontalLayout();
        private final Panel Patientspanel = new Panel("List of Patient");
        private final Button update = new Button("Update Patient");
        private final Button delete = new Button("Delete  Patient");
        private final Button add = new Button("Add new Patient");
        private final Button addingAcceptButton = new Button("Ok     ");
        private final Button addingDeclineButton = new Button("Decline");
        private final Button updateAcceptButton = new Button("Ok     ");
        private final Button updateDeclineButton = new Button("Decline");
        private final VerticalLayout dbl = new VerticalLayout(add, update, delete);
        private final Panel Patientbuttons = new Panel(dbl);
        private PatientDAO patdao = new PatientDAO();
        List<Patient> patlist = new ArrayList<Patient>();
        private final String regexW = "\\w+";
        private final String regexN = "\\d+";
        List<Recipe> reclist = new ArrayList<Recipe>();
        private RecipeDAO recdao = new RecipeDAO();

        public HorizontalLayout getMainPatientPane() {
                AddButtonBinding();
                patAddWinSetting();
                UpdateButtonBinding();
                patUpdateWinSetting();
                DeleteButtonBinding();
                gridPatient.setWidth(1000.0f, Unit.PIXELS);
                gridPatient.setColumnOrder("id", "name", "surname", "patronymic", "telephone");
                gridPatient.setSelectionMode(Grid.SelectionMode.SINGLE);
                updatPatGrid();
                Patientspanel.setContent(gridPatient);
                HlayoutPatient.addComponent(Patientspanel);
                HlayoutPatient.addComponent(Patientbuttons);
                return HlayoutPatient;
        }

        private void patAddWinSetting() {
                HlayoutAdd.addComponents(addingAcceptButton, addingDeclineButton);
                subContentAdd.addComponents(addName, addSurname, addPatronymic, addTelephone, HlayoutAdd);
                addPatientWindow.setContent(subContentAdd);
                addPatientWindow.setWidth(220.0f, Unit.PIXELS);
                addPatientWindow.center();
                addPatientWindow.setModal(true);
        }

        private void patUpdateWinSetting() {
                HlayoutUpdate.addComponents(updateAcceptButton, updateDeclineButton);
                subContentUpdate.addComponents(updateName, updateSurname, updatePatronymic, updateTelephone, HlayoutUpdate);
                updatePatientWindow.setContent(subContentUpdate);
                updatePatientWindow.setWidth(220.0f, Unit.PIXELS);
                updatePatientWindow.center();
                updatePatientWindow.setModal(true);
        }

        private void AddButtonBinding() {
                add.addClickListener(event -> {
                        if (!addPatientWindow.isAttached()) {
                                UI.getCurrent().addWindow(addPatientWindow);
                                Notification.show("Adding new Patient", Type.TRAY_NOTIFICATION);
                        }
                });
                addingDeclineButton.addClickListener(event -> {
                        addPatientWindow.close();
                });
                addingAcceptButton.addClickListener(event -> {
                        boolean permition = true;
                        if (addName.getValue().matches(regexN) || !addName.getValue().matches(regexW) || addName.getValue().length() > 30 || addName.getValue() == "") {
                                permition = false;
                                addName.setValue("incorrect input");
                        }
                        if (addSurname.getValue().matches(regexN) || !addSurname.getValue().matches(regexW) || addSurname.getValue().length() > 30 || addSurname.getValue() == "") {
                                permition = false;
                                addSurname.setValue("incorrect input");
                        }
                        if (addPatronymic.getValue().matches(regexN) || !addPatronymic.getValue().matches(regexW) || addPatronymic.getValue().length() > 30 || addPatronymic.getValue() == "") {
                                permition = false;
                                addPatronymic.setValue("incorrect input");
                        }
                        if (!addTelephone.getValue().matches(regexN) || addTelephone.getValue().length() > 30 || addTelephone.getValue() == "") {
                                permition = false;
                                addTelephone.setValue("incorrect input");
                        }
                        if (permition) {
                                Patient pat = new Patient(0, addName.getValue(), addSurname.getValue(), addPatronymic.getValue(), addTelephone.getValue());
                                if (patdao.createInTableBySubject(pat)) {
                                        addPatientWindow.close();
                                        Notification.show("Patient " + addName.getValue() + " " + addSurname.getValue() + " " + addPatronymic.getValue() + " Added", Type.TRAY_NOTIFICATION);
                                        updatPatGrid();
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
                        if (!updatePatientWindow.isAttached()) {
                                SingleSelect<Patient> Selection = gridPatient.asSingleSelect();
                                if (Selection.getValue() != null) {
                                        Patient pat = new Patient();
                                        pat = Selection.getValue();
                                        updateName.setValue(pat.getName());
                                        updateSurname.setValue(pat.getSurname());
                                        updatePatronymic.setValue(pat.getPatronymic());
                                        updateTelephone.setValue(pat.getTelephone());
                                        UI.getCurrent().addWindow(updatePatientWindow);
                                        Notification.show("Open Patient Redactor", Type.TRAY_NOTIFICATION);
                                } else {
                                        Notification.show("choose Patient in a table ", Type.ERROR_MESSAGE);
                                }
                        }
                });
                updateDeclineButton.addClickListener(event -> {
                        updatePatientWindow.close();
                });
                updateAcceptButton.addClickListener(event -> {
                        boolean permition = true;
                        if (updateName.getValue().matches(regexN) || !updateName.getValue().matches(regexW) || updateName.getValue().length() > 30 || updateName.getValue() == "") {
                                permition = false;
                                updateName.setValue("incorrect input");
                        }
                        if (updateSurname.getValue().matches(regexN) || !updateSurname.getValue().matches(regexW) || updateSurname.getValue().length() > 30 || updateSurname.getValue() == "") {
                                permition = false;
                                updateSurname.setValue("incorrect input");
                        }
                        if (updatePatronymic.getValue().matches(regexN) || !updatePatronymic.getValue().matches(regexW) || updatePatronymic.getValue().length() > 30 || updatePatronymic.getValue() == "") {
                                permition = false;
                                updatePatronymic.setValue("incorrect input");
                        }
                        if (!updateTelephone.getValue().matches(regexN) || updateTelephone.getValue().length() > 30 || updateTelephone.getValue() == "") {
                                permition = false;
                                updateTelephone.setValue("incorrect input");
                        }
                        if (permition) {
                                Patient pat = new Patient();
                                SingleSelect<Patient> Selection = gridPatient.asSingleSelect();
                                pat = Selection.getValue();
                                if (Selection.getValue() == null) {
                                        Notification.show("Patient disapeard", Type.ERROR_MESSAGE);
                                } else {
                                        pat.setName(updateName.getValue());
                                        pat.setSurname(updateSurname.getValue());
                                        pat.setPatronymic(updatePatronymic.getValue());
                                        pat.setTelephone(updateTelephone.getValue());
                                        if (patdao.updateInTableBySubject(pat)) {
                                                Notification.show("Patient " + pat.getName() + " " + pat.getSurname() + " " + pat.getPatronymic() + " Updated", Type.TRAY_NOTIFICATION);
                                                updatPatGrid();
                                        } else {
                                                Notification.show("Some syntax Error", Type.ERROR_MESSAGE);
                                        }
                                }
                                updatePatientWindow.close();
                        } else {
                                Notification.show("Correct the fields", Type.ERROR_MESSAGE);
                        }
                });
        }

        private void DeleteButtonBinding() {
                delete.addClickListener(event -> {
                        Patient pat = new Patient();
                        SingleSelect<Patient> Selection = gridPatient.asSingleSelect();
                        pat = Selection.getValue();
                        if (Selection.getValue() == null) {
                                Notification.show("choose Patient in a table ", Type.ERROR_MESSAGE);
                        } else {
                                reclist.clear();
                                reclist = recdao.readToSubjectList();
                                boolean check = true;
                                for (int i = 0; i < reclist.size(); i++) {
                                        if (reclist.get(i).getPatient().getId().equals(pat.getId())) {
                                                check = false;
                                                break;
                                        }
                                }
                                if (check) {

                                        if (patdao.deleteInTableBySubject(pat)) {
                                                Notification.show("Patient " + pat.getName() + " " + pat.getSurname() + " " + pat.getPatronymic() + " Deleted", Type.TRAY_NOTIFICATION);
                                                updatPatGrid();
                                        } else {
                                                Notification.show("Syntax error", Type.ERROR_MESSAGE);
                                        }
                                } else {
                                        Notification.show("Recipe with this Patient is exist", Type.ERROR_MESSAGE);
                                }

                        }
                });
        }

        private void updatPatGrid() {
                patlist.clear();
                patlist = patdao.readToSubjectList();
                gridPatient.setItems(patlist);
        }

}
