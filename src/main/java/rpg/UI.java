package rpg;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class UI {
    private final GridPane gridPane;
    private final CheckBox useLowerCase;
    private final CheckBox useUpperCase;
    private final CheckBox useNumbers;
    private final Label lowerCaseLabel;
    private final Label upperCaseLabel;
    private final Label numbersLabel;
    private final Label charactersLabel;
    private final CheckBox useCharacters;
    private final Slider numberOfCharacters;
    private final Label numberOfCharactersLabel;
    private final Text numberOfChartersOutput;
    private final Button generatePassword;
    private final Label passwordLabel;
    private final TextField passwordOutput;
    private int passwordLength;
    private final Button copyPassword;
    private String password;
    private Boolean choiceSelected;
    private final String lowercaseLetters;
    private final String uppercaseLetters;
    private final String numbers;
    private final String characters;
    private Boolean needsLowerCase;
    private Boolean needsUpperCase;
    private Boolean needsNumbers;
    private Boolean needsCharacters;
    private Boolean passwordIsGood;


    public UI(Generator generator){
        this.needsLowerCase = false;
        this.needsUpperCase = false;
        this.needsNumbers = false;
        this.needsCharacters = false;
        this.passwordIsGood = false;
        this.password="";
        this.gridPane = new GridPane();
        this.gridPane.setPadding(new Insets(10, 10, 10, 10));
        this.gridPane.setVgap(10);
        this.gridPane.setHgap(10);
        this.gridPane.setMinSize(500, 300);

        this.lowercaseLetters= "abcdefghijklmnopqrstuvwxyz";
        this.uppercaseLetters= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.numbers= "0123456789";
        this.characters= "!@#$%^&*()+";


        this.lowerCaseLabel = new Label("Include Lowercase Characters:");
        this.useLowerCase = new CheckBox("(e.g. " +this.lowercaseLetters+ ")");

        this.upperCaseLabel = new Label("Include Uppercase Characters:");
        this.useUpperCase = new CheckBox("(e.g. "+ this.uppercaseLetters+ ")");

        this.numbersLabel = new Label("Include Numbers:");
        this.useNumbers = new CheckBox("(e.g. 0 1 2 3 4 5 6 7 8 9)");

        this.charactersLabel = new Label("Include Characters:");
        this.useCharacters = new CheckBox("e.g. ! @ # $ % ^ & * ( ) +");

        this.numberOfChartersOutput = new Text("16");

        this.numberOfCharacters = new Slider();
        this.numberOfCharacters.setMin(8);
        this.numberOfCharacters.setMax(32);
        this.numberOfCharacters.setValue(16);
        this.passwordLength=16;
        this.numberOfCharacters.setBlockIncrement(1);
        this.numberOfCharacters.setMajorTickUnit(4);
        this.numberOfCharacters.setShowTickMarks(true);
        this.numberOfCharacters.setShowTickLabels(true);
        this.numberOfCharacters.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    numberOfChartersOutput.setText(newValue.shortValue()+"");
                     this.passwordLength= newValue.shortValue();

                });

        this.numberOfCharactersLabel = new Label("Password Length:");

        this.generatePassword = new Button("Generate Password");
        this.generatePassword.setOnMouseClicked(e-> this.generatePassword(generator));

        this.copyPassword = new Button("Copy Password to Clipboard");
        this.copyPassword.setOnMouseClicked(e-> this.copyPassword());

        this.passwordLabel = new Label("Your New Password:");
        this.passwordOutput = new TextField();
        this.choiceSelected = false;
    }

    public void buildLayout(){
        this.gridPane.add(lowerCaseLabel,1,1);
        this.gridPane.add(useLowerCase, 2,1);

        this.gridPane.add(upperCaseLabel,1,2);
        this.gridPane.add(useUpperCase,2,2);

        this.gridPane.add(numbersLabel,1,3);
        this.gridPane.add(useNumbers,2,3);

        this.gridPane.add(charactersLabel, 1,4);
        this.gridPane.add(useCharacters,2,4);

        this.gridPane.add(numberOfCharactersLabel,1,5);
        this.gridPane.add(numberOfCharacters,2,5);
        this.gridPane.add(numberOfChartersOutput,3,5);

        this.gridPane.add(generatePassword,2,6);
        this.gridPane.add(copyPassword,2,8);

        this.gridPane.add(passwordLabel,1,7);
        this.gridPane.add(passwordOutput,2,7,2,1);
    }

    public GridPane getGridPane() {
        return this.gridPane;
    }

    public void displayErrorMessage(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Option");
        alert.setHeaderText("Please select at least on option.");
        alert.setContentText("Unable to generate a password with no options selected.");
        alert.show();
    }

    public void copyPassword(){
        StringSelection stringSelectionObj = new StringSelection(this.password);
        Clipboard clipboardObj = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboardObj.setContents(stringSelectionObj, null);
    }

    public void generatePassword(Generator generator){
        this.passwordIsGood = false;
        while (!this.passwordIsGood) {
            generator.clearList();
            this.choiceSelected = false;
            this.needsLowerCase = false;
            this.needsUpperCase = false;
            this.needsNumbers = false;
            this.needsCharacters = false;

            if (this.useLowerCase.isSelected()) {
                this.choiceSelected = true;
                this.needsLowerCase = true;
                generator.addToList(this.lowercaseLetters);
            }
            if (this.useUpperCase.isSelected()) {
                this.choiceSelected = true;
                this.needsUpperCase = true;
                generator.addToList(this.uppercaseLetters);
            }
            if (this.useCharacters.isSelected()) {
                this.choiceSelected = true;
                this.needsCharacters = true;
                generator.addToList(this.characters);
            }
            if (this.useNumbers.isSelected()) {
                this.choiceSelected = true;
                this.needsNumbers = true;
                generator.addToList(this.numbers);
            }

            if (!this.choiceSelected) {
                this.displayErrorMessage();
            }

            System.out.println(this.passwordLength);
            this.password = generator.generatePassword(this.passwordLength);

            this.checkPassword();

            System.out.println(this.password);
            this.passwordOutput.setText(this.password);
        }
    }

    public void checkPassword() {
        // Check the password to make sure that it contains all the choices selected.
        if (this.needsLowerCase) {
            for (int i = 0; i < this.lowercaseLetters.length(); i++) {
                if (this.password.contains(String.valueOf(this.lowercaseLetters.charAt(i)))) {
                    this.needsLowerCase = false;
                    break;
                }
            }
        }

        if (this.needsUpperCase) {
            for (int i = 0; i < this.uppercaseLetters.length(); i++) {
                if (this.password.contains(String.valueOf(this.uppercaseLetters.charAt(i)))) {
                    this.needsUpperCase = false;
                    break;
                }
            }
        }

        if (this.needsNumbers) {
            for (int i = 0; i < this.numbers.length(); i++) {
                if (this.password.contains(String.valueOf(this.numbers.charAt(i)))) {
                    this.needsNumbers = false;
                    break;
                }
            }
        }

        if (this.needsCharacters) {
            for (int i = 0; i < this.characters.length(); i++) {
                if (this.password.contains(String.valueOf(this.characters.charAt(i)))) {
                    this.needsCharacters = false;
                    break;
                }
            }
        }

        if (this.needsUpperCase || this.needsLowerCase || this.needsNumbers || this.needsCharacters) {
            System.out.println("NEED NEW PASSWORD, DOES NOT MEET ALL OF SELECTED CRITERIA");
        } else {
            System.out.println("PASSWORD IS GOOD");
            this.passwordIsGood = true;
        }
    }


}

