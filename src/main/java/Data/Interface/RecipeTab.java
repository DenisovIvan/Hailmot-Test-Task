package Data.Interface;

import Data.DAO.DoctorDAO;
import Data.DAO.PatientDAO;
import Data.DAO.RecipeDAO;
import Data.Things.Doctor;
import Data.Things.Patient;
import Data.Things.Recipe;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.SingleSelect;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecipeTab {

        private final Grid<Recipe> gridRecipe = new Grid<>();
        private final Window addRecipeWindow = new Window("Add a new Recipe");
        private final Window updateRecipeWindow = new Window("Update a Recipe");
        private final Window choosePatientWindow = new Window("Choose Patient");
        private final Grid<Patient> gridPatient = new Grid<>(Patient.class);
        private final Grid<Doctor> gridDoctor = new Grid<>(Doctor.class);
        private final Window chooseDoctorWindow = new Window("Choose Doctor");
        private final TextArea addDiscrption = new TextArea("Insert Discription");
        private final TextField addValidaty = new TextField("Insert Validaty (in days)");
        private final TextField addPatient = new TextField("Insert Patient Id");
        private final TextField updatePatient = new TextField("Insert Patient Id");
        private final TextField addDoctor = new TextField("Insert Doctor Id");
        private final TextField updateDoctor = new TextField("Insert Doctor Id");
        private final DateTimeField addDate = new DateTimeField("Choose Date of Create");
        private final DateTimeField updateDate = new DateTimeField("Choose Date of Create");
        private final ComboBox addPriority = new ComboBox("Choose Priority");
        private final ComboBox updatePriority = new ComboBox("Choose Priority");
        private final TextArea updateDiscription = new TextArea("Insert Discription");
        private final TextField updateValidaty = new TextField("Insert validaty( in days)");
        private final VerticalLayout subContentAdd = new VerticalLayout();
        private final VerticalLayout subContentUpdate = new VerticalLayout();
        private final VerticalLayout MainLayout = new VerticalLayout();
        private final HorizontalLayout HlayoutfilterRecipe = new HorizontalLayout();
        private final TextField RDFilter = new TextField("Recipe Discription Filter");
        private final TextField RPFilter = new TextField("Recipe Patient Filter ");
        private final ComboBox RPrFilter = new ComboBox<>("Recipe Priority Filter");
        private final HorizontalLayout HlayoutRecipe = new HorizontalLayout();
        private final HorizontalLayout HlayoutAdd = new HorizontalLayout();
        private final HorizontalLayout HlayoutUpdate = new HorizontalLayout();
        private final HorizontalLayout HlayoutAddChoosePatient = new HorizontalLayout();
        private final HorizontalLayout HlayoutAddChooseDoctor = new HorizontalLayout();
        private final HorizontalLayout HlayoutUpdateChoosePatient = new HorizontalLayout();
        private final HorizontalLayout HlayoutUpdateChooseDoctor = new HorizontalLayout();
        private final Panel Recipespanel = new Panel("List of Recipe");
        private final Button chooseAddPatient = new Button("Choose Patient");
        private final Button chooseAddDoctor = new Button("Choose Doctor");
        private final Button chooseUpdatePatient = new Button("Choose Patient");
        private final Button chooseUpdateDoctor = new Button("Choose Doctor");
        private final Button update = new Button("Update Recipe");
        private final Button delete = new Button("Delete  Recipe");
        private final Button add = new Button("Add new Recipe");
        private final Button addingAcceptButton = new Button("Ok     ");
        private final Button addingDeclineButton = new Button("Decline");
        private final Button updateAcceptButton = new Button("Ok     ");
        private final Button updateDeclineButton = new Button("Decline");
        private final VerticalLayout dbl = new VerticalLayout(add, update, delete);
        private final Panel Recipebuttons = new Panel(dbl);
        private RecipeDAO recdao = new RecipeDAO();
        private final String regexW = "\\w+";
        private final String regexN = "\\d+";
        private final String regexNonDigit = "\\D+";
        private final String regexDigit = "\\d+";
        List<Recipe> reclist = new ArrayList<Recipe>();
        List<Recipe> reclistfiltered = new ArrayList<Recipe>();
        private PatientDAO patdao = new PatientDAO();
        List<Patient> patlist = new ArrayList<Patient>();
        private DoctorDAO docdao = new DoctorDAO();
        List<Doctor> doclist = new ArrayList<Doctor>();

        public VerticalLayout getMainRecipePane() {
                AddButtonBinding();
                recAddWinSetting();
                UpdateButtonBinding();
                recUpdateWinSetting();
                DeleteButtonBinding();
                patientGridSetting();
                doctorGridSetting();
                filterBinding();
                HlayoutfilterRecipe.addComponents(RDFilter, RPFilter, RPrFilter);
                gridRecipe.setWidth(1200.0f, Unit.PIXELS);
                gridRecipe.addColumn(Recipe::getId).setCaption("id");
                gridRecipe.addColumn(Recipe::getDiscription).setCaption("discription");
                gridRecipe.getColumns().get(0).setWidth(80);
                gridRecipe.getColumns().get(1).setWidth(300);
                gridRecipe.addColumn(Recipe::getPatInfo).setCaption("patient");
                gridRecipe.addColumn(Recipe::getDocInfo).setCaption("doctor");
                gridRecipe.addColumn(Recipe::getCalendar).setCaption("date of creation");
                gridRecipe.addColumn(Recipe::getValidaty).setCaption("validaty");
                gridRecipe.addColumn(recipePriority -> recipePriority.getPriority().getName()).setCaption("priority");
                gridRecipe.setSelectionMode(Grid.SelectionMode.SINGLE);
                updateRecGrid();
                Recipespanel.setContent(gridRecipe);
                HlayoutRecipe.addComponent(Recipespanel);
                HlayoutRecipe.addComponent(Recipebuttons);
                MainLayout.addComponents(HlayoutfilterRecipe, HlayoutRecipe);
                return MainLayout;
        }

        private void recAddWinSetting() {
                addPriority.setEmptySelectionAllowed(false);
                addPriority.setItems("Normal 1", "Cito 2", "Statim 3");
                HlayoutAdd.addComponents(addingAcceptButton, addingDeclineButton);
                HlayoutAddChoosePatient.addComponents(addPatient, chooseAddPatient);
                HlayoutAddChooseDoctor.addComponents(addDoctor, chooseAddDoctor);
                HlayoutAddChoosePatient.setComponentAlignment(chooseAddPatient, Alignment.BOTTOM_CENTER);
                HlayoutAddChooseDoctor.setComponentAlignment(chooseAddDoctor, Alignment.BOTTOM_CENTER);
                subContentAdd.addComponents(addDiscrption, HlayoutAddChoosePatient, HlayoutAddChooseDoctor, addDate, addValidaty, addPriority, HlayoutAdd);
                addRecipeWindow.setContent(subContentAdd);
                addDiscrption.setWidth(300.0f, Unit.PIXELS);
                addDiscrption.setHeight(100.0f, Unit.PIXELS);
                addRecipeWindow.setWidth(400.0f, Unit.PIXELS);
                addRecipeWindow.center();
                addRecipeWindow.setModal(true);
        }

        private void recUpdateWinSetting() {
                updatePriority.setEmptySelectionAllowed(false);
                updatePriority.setItems("Normal 1", "Cito 2", "Statim 3");
                HlayoutUpdate.addComponents(updateAcceptButton, updateDeclineButton);
                HlayoutUpdateChoosePatient.addComponents(updatePatient, chooseUpdatePatient);
                HlayoutUpdateChooseDoctor.addComponents(updateDoctor, chooseUpdateDoctor);
                HlayoutUpdateChoosePatient.setComponentAlignment(chooseUpdatePatient, Alignment.BOTTOM_CENTER);
                HlayoutUpdateChooseDoctor.setComponentAlignment(chooseUpdateDoctor, Alignment.BOTTOM_CENTER);
                subContentUpdate.addComponents(updateDiscription, HlayoutUpdateChoosePatient, HlayoutUpdateChooseDoctor, updateDate, updateValidaty, updatePriority, HlayoutUpdate);
                updateRecipeWindow.setContent(subContentUpdate);
                updateRecipeWindow.setWidth(400.0f, Unit.PIXELS);
                updateDiscription.setWidth(300.0f, Unit.PIXELS);
                updateDiscription.setHeight(100.0f, Unit.PIXELS);
                updateRecipeWindow.center();
                updateRecipeWindow.setModal(true);
        }

        private void patientGridSetting() {
                gridPatient.setWidth(600.0f, Unit.PIXELS);
                gridPatient.setSelectionMode(Grid.SelectionMode.SINGLE);
                gridPatient.setItems(patdao.readToSubjectList());
                choosePatientWindow.setContent(gridPatient);
                choosePatientWindow.center();
                choosePatientWindow.setModal(true);
                gridPatient.setColumnOrder("id", "name", "surname", "patronymic", "telephone");
                gridPatient.addSelectionListener(event -> {
                        SingleSelect<Patient> Selection = gridPatient.asSingleSelect();
                        if (Selection.getValue() != null) {
                                addPatient.setValue(String.valueOf(Selection.getValue().getId()));
                                updatePatient.setValue(String.valueOf(Selection.getValue().getId()));
                                choosePatientWindow.close();
                        }
                });
                chooseAddPatient.addClickListener(event -> {
                        if (!choosePatientWindow.isAttached()) {
                                gridPatient.setItems(patdao.readToSubjectList());
                                UI.getCurrent().addWindow(choosePatientWindow);
                                Notification.show("Click on Table", Type.TRAY_NOTIFICATION);
                        }
                });
                chooseUpdatePatient.addClickListener(event -> {
                        if (!choosePatientWindow.isAttached()) {
                                gridPatient.setItems(patdao.readToSubjectList());
                                UI.getCurrent().addWindow(choosePatientWindow);
                                Notification.show("Click on Table", Type.TRAY_NOTIFICATION);
                        }
                });
        }

        private void doctorGridSetting() {
                gridDoctor.setWidth(600.0f, Unit.PIXELS);
                gridDoctor.setSelectionMode(Grid.SelectionMode.SINGLE);
                gridDoctor.setItems(docdao.readToSubjectList());
                chooseDoctorWindow.setContent(gridDoctor);
                chooseDoctorWindow.center();
                chooseDoctorWindow.setModal(true);
                gridDoctor.setColumnOrder("id", "name", "surname", "patronymic", "specialization");
                gridDoctor.removeColumn("statistic");
                gridDoctor.removeColumn("infoStatistic");
                gridDoctor.addSelectionListener(event -> {
                        SingleSelect<Doctor> Selection = gridDoctor.asSingleSelect();
                        if (Selection.getValue() != null) {
                                addDoctor.setValue(String.valueOf(Selection.getValue().getId()));
                                updateDoctor.setValue(String.valueOf(Selection.getValue().getId()));
                                chooseDoctorWindow.close();
                        }
                });
                chooseAddDoctor.addClickListener(event -> {
                        if (!chooseDoctorWindow.isAttached()) {
                                gridDoctor.setItems(docdao.readToSubjectList());
                                UI.getCurrent().addWindow(chooseDoctorWindow);
                                Notification.show("Click on Table", Type.TRAY_NOTIFICATION);
                        }
                });
                chooseUpdateDoctor.addClickListener(event -> {
                        if (!chooseDoctorWindow.isAttached()) {
                                gridDoctor.setItems(docdao.readToSubjectList());
                                UI.getCurrent().addWindow(chooseDoctorWindow);
                                Notification.show("Click on Table", Type.TRAY_NOTIFICATION);
                        }
                });
        }

        private void AddButtonBinding() {
                add.addClickListener(event -> {
                        if (!addRecipeWindow.isAttached()) {
                                UI.getCurrent().addWindow(addRecipeWindow);
                                Notification.show("Adding new Recipe", Type.TRAY_NOTIFICATION);
                        }
                });
                addingDeclineButton.addClickListener(event -> {
                        addRecipeWindow.close();
                });
                addingAcceptButton.addClickListener(event -> {
                        boolean permition = true;
                        if (addDiscrption.getValue().length() > 150 || addDiscrption.getValue() == "") {
                                permition = false;
                                addDiscrption.setValue("incorrect input");
                        }
                        if (addPriority.isEmpty()) {
                                permition = false;
                                Notification.show("Choose Priority ", Type.ERROR_MESSAGE);
                        }
                        if (!addPatient.getValue().matches(regexN) || addPatient.getValue().length() > 30 || addPatient.getValue() == "") {
                                permition = false;
                                addPatient.setValue("incorrect input");
                        }
                        if (!addDoctor.getValue().matches(regexN) || addDoctor.getValue().length() > 30 || addDoctor.getValue() == "") {
                                permition = false;
                                addDoctor.setValue("incorrect input");
                        }
                        if (!addValidaty.getValue().matches(regexN) || addValidaty.getValue().length() > 30 || addValidaty.getValue() == "") {
                                permition = false;
                                addValidaty.setValue("incorrect input");
                        }
                        if (addDate.isEmpty()) {
                                permition = false;
                                Notification.show("Choose correct date", Type.ERROR_MESSAGE);
                        }

                        if (permition) {
                                Recipe.RecipePatient rpat = new Recipe.RecipePatient(Long.valueOf(addPatient.getValue()));
                                Recipe.RecipeDoctor rdoc = new Recipe.RecipeDoctor(Long.valueOf(addDoctor.getValue()));
                                Recipe.RecipePriority rrec = new Recipe.RecipePriority(Long.valueOf(String.valueOf(addPriority.getValue()).replaceAll(regexNonDigit, "")));
                                java.util.Date calendar = Date.from(addDate.getValue().atZone(ZoneId.systemDefault()).toInstant());
                                Recipe recipe = new Recipe(0, addDiscrption.getValue(), rpat, rdoc, calendar, Integer.valueOf(addValidaty.getValue()), rrec);
                                if (recdao.createInTableBySubject(recipe)) {
                                        updateRecGrid();
                                        Notification.show("Recipe with Discripton : " + addDiscrption.getValue(), Type.TRAY_NOTIFICATION);
                                        addRecipeWindow.close();
                                } else {
                                        Notification.show("Doctor or Patient not found ", Type.ERROR_MESSAGE);
                                }
                        } else {
                                Notification.show("Correct the fields", Type.ERROR_MESSAGE);
                        }
                });
        }

        private void UpdateButtonBinding() {
                update.addClickListener(event -> {
                        if (!updateRecipeWindow.isAttached()) {
                                SingleSelect<Recipe> Selection = gridRecipe.asSingleSelect();
                                if (Selection.getValue() != null) {
                                        Recipe rec = new Recipe();
                                        rec = Selection.getValue();
                                        updateDiscription.setValue(rec.getDiscription());
                                        updatePatient.setValue(String.valueOf(rec.getPatient().getId()));
                                        updateDoctor.setValue(String.valueOf(rec.getDoctor().getId()));
                                        updateDate.setValue(LocalDateTime.ofInstant(rec.getCalendar().toInstant(), ZoneId.systemDefault()));
                                        updateValidaty.setValue(String.valueOf(rec.getValidaty()));
                                        updatePriority.setValue(String.valueOf(rec.getPriority().getId()));
                                        UI.getCurrent().addWindow(updateRecipeWindow);
                                        Notification.show("Open Recipe Redactor", Type.TRAY_NOTIFICATION);
                                } else {
                                        Notification.show("choose Recipe in a table ", Type.ERROR_MESSAGE);
                                }
                        }
                });
                updateDeclineButton.addClickListener(event -> {
                        updateRecipeWindow.close();
                });
                updateAcceptButton.addClickListener(event -> {
                        boolean permition = true;
                        if (updateDiscription.getValue().length() > 150 || updateDiscription.getValue() == "") {
                                permition = false;
                                updateDiscription.setValue("incorrect input");
                        }
                        if (updatePriority.isEmpty()) {
                                permition = false;
                                Notification.show("Choose Priority ", Type.ERROR_MESSAGE);
                        }
                        if (!updatePatient.getValue().matches(regexN) || updatePatient.getValue().length() > 30 || updatePatient.getValue() == "") {
                                permition = false;
                                updatePatient.setValue("incorrect input");
                        }
                        if (!updateDoctor.getValue().matches(regexN) || updateDoctor.getValue().length() > 30 || updateDoctor.getValue() == "") {
                                permition = false;
                                updateDoctor.setValue("incorrect input");
                        }
                        if (!updateValidaty.getValue().matches(regexN) || updateValidaty.getValue().length() > 30 || updateValidaty.getValue() == "") {
                                permition = false;
                                updateValidaty.setValue("incorrect input");
                        }
                        if (updateDate.isEmpty()) {
                                permition = false;
                                Notification.show("Choose correct date", Type.ERROR_MESSAGE);
                        }
                        if (permition) {
                                if (gridRecipe.asSingleSelect() != null) {
                                        Recipe.RecipePatient rpat = new Recipe.RecipePatient(Long.valueOf(updatePatient.getValue()));
                                        Recipe.RecipeDoctor rdoc = new Recipe.RecipeDoctor(Long.valueOf(updateDoctor.getValue()));
                                        Recipe.RecipePriority rrec = new Recipe.RecipePriority(Long.valueOf(String.valueOf(updatePriority.getValue()).replaceAll(regexNonDigit, "")));
                                        java.util.Date calendar = Date.from(updateDate.getValue().atZone(ZoneId.systemDefault()).toInstant());
                                        Recipe recipe = new Recipe(Long.valueOf(gridRecipe.asSingleSelect().getValue().getId()), updateDiscription.getValue(), rpat, rdoc, calendar, Integer.valueOf(updateValidaty.getValue()), rrec);
                                        if (recdao.updateInTableBySubject(recipe)) {
                                                updateRecGrid();
                                                Notification.show("Recipe with Discripton : " + updateDiscription.getValue() + " was  updated", Type.TRAY_NOTIFICATION);
                                                updateRecipeWindow.close();
                                        } else {
                                                Notification.show("Doctor or Patient not found ", Type.ERROR_MESSAGE);
                                        }
                                } else {
                                        Notification.show("Recipe Disapered", Type.ERROR_MESSAGE);
                                }
                        } else {
                                Notification.show("Correct the fields", Type.ERROR_MESSAGE);
                        }
                });
        }

        private void DeleteButtonBinding() {
                delete.addClickListener(event -> {
                        Recipe rec = new Recipe();
                        SingleSelect<Recipe> Selection = gridRecipe.asSingleSelect();
                        rec = Selection.getValue();
                        if (Selection.getValue() == null) {
                                Notification.show("choose Recipe in a table ", Type.ERROR_MESSAGE);
                        } else {
                                if (recdao.deleteInTableBySubject(rec)) {
                                        Notification.show("Recipe " + rec.getDiscription() + " Deleted", Type.TRAY_NOTIFICATION);
                                        updateRecGrid();
                                } else {
                                        Notification.show("Some syntax Error", Type.ERROR_MESSAGE);
                                }
                        }
                });
        }

        private void filterBinding() {
                RPrFilter.setItems("", "Normal", "Cito", "Statim");
                RPrFilter.setSelectedItem("");
                RPrFilter.setEmptySelectionAllowed(false);
                RDFilter.addValueChangeListener(event -> {
                        filtering();
                });
                RPFilter.addValueChangeListener(event -> {
                        filtering();
                });
                RPrFilter.addValueChangeListener(event -> {
                        filtering();
                });
        }

        private void filtering() {
                String discriptiontext = RDFilter.getValue();
                String patientext = RPFilter.getValue();
                String priority = String.valueOf(RPrFilter.getValue());
                reclistfiltered.clear();
                for (int i = 0; i < reclist.size(); i++) {
                        Recipe onerec = reclist.get(i);
                        if (onerec.getDiscription().contains(discriptiontext) && onerec.getPatInfo().contains(patientext) && onerec.getPriority().getName().contains(priority)) {
                                reclistfiltered.add(onerec);
                        }
                }
                gridRecipe.setItems(reclistfiltered);
        }

        private void updateRecGrid() {
                reclist.clear();
                reclist = recdao.readToSubjectList();
                gridRecipe.setItems(reclist);
                filtering();
        }
}
