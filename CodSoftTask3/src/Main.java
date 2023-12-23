import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private ATM atm;
    private AccountHolderDatabase userDatabase;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ATM JavaFX");

        userDatabase = new AccountHolderDatabase(); 

        atm = new ATM(userDatabase);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 50, 50, 50));

        Label label = new Label("Welcome to the ATM!");
        vbox.getChildren().add(label);

        Button createAccountBtn = new Button("Create Account");
        Button logInBtn = new Button("Log In");
        Button exitBtn = new Button("Exit");

        createAccountBtn.setOnAction(e -> createAccount());
        logInBtn.setOnAction(e -> logIn());
        exitBtn.setOnAction(e -> primaryStage.close());

        vbox.getChildren().addAll(createAccountBtn, logInBtn, exitBtn);

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void createAccount() {
        Stage createAccountStage = new Stage();
        createAccountStage.setTitle("Create Account");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 50, 50, 50));

        Label nameLabel = new Label("Enter your name:");
        TextField nameField = new TextField();
        Label initialBalanceLabel = new Label("Enter initial balance:");
        TextField balanceField = new TextField();

        Button createAccountBtn = new Button("Create Account");

        createAccountBtn.setOnAction(e -> {
            String userName = nameField.getText();
            double initialBalance = Double.parseDouble(balanceField.getText());

            int accountNumber = (int) (Math.random() * 100000000);
            userDatabase.addAccountHolder(accountNumber, initialBalance);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account Created");
            alert.setHeaderText(null);
            alert.setContentText("Your account has been created Sucessfully. Account number: " + accountNumber);
            alert.showAndWait();

            createAccountStage.close();
        });

        vbox.getChildren().addAll(nameLabel, nameField, initialBalanceLabel, balanceField, createAccountBtn);

        Scene scene = new Scene(vbox, 300, 200);
        createAccountStage.setScene(scene);
        createAccountStage.show();
    }

    private void logIn() {
        Stage logInStage = new Stage();
        logInStage.setTitle("Log In");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 50, 50, 50));

        Label accountNumberLabel = new Label("Enter your account number:");
        TextField accountNumberField = new TextField();

        Button logInBtn = new Button("Log In");
        logInBtn.setOnAction(e -> showLoggedInMenu(Integer.parseInt(accountNumberField.getText())));
       // checkBalanceBtn.setOnAction(e -> showCheckBalanceDialog(accountNumber));

        vbox.getChildren().addAll(accountNumberLabel, accountNumberField, logInBtn);

        Scene scene = new Scene(vbox, 300, 150);
        logInStage.setScene(scene);
        logInStage.show();
    }

    private void showLoggedInMenu(int accountNumber) {
        Stage loggedInStage = new Stage();
        loggedInStage.setTitle("ATM - Logged In");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 50, 50, 50));

        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        Button checkBalanceBtn = new Button("Check Balance");
        Button exitBtn = new Button("Exit");

        depositBtn.setOnAction(e -> showDepositDialog(accountNumber));
        withdrawBtn.setOnAction(e -> showWithdrawDialog(accountNumber));
        checkBalanceBtn.setOnAction(e -> showCheckBalanceDialog(accountNumber));
        exitBtn.setOnAction(e -> loggedInStage.close());

        vbox.getChildren().addAll(depositBtn, withdrawBtn, checkBalanceBtn, exitBtn);

        Scene scene = new Scene(vbox, 300, 200);
        loggedInStage.setScene(scene);
        loggedInStage.show();
    }

    private void showDepositDialog(int accountNumber) {
        Stage depositStage = new Stage();
        depositStage.setTitle("Deposit");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 50, 50, 50));

        Label amountLabel = new Label("Enter amount you want to deposit:");
        TextField amountField = new TextField();

        Button depositBtn = new Button("Deposit");
        depositBtn.setOnAction(e -> {
            double depositAmount = Double.parseDouble(amountField.getText());
            String message = atm.deposit(accountNumber, depositAmount);
            showAlert("Transaction Result", message);
            depositStage.close();
        });

        vbox.getChildren().addAll(amountLabel, amountField, depositBtn);

        Scene scene = new Scene(vbox, 300, 150);
        depositStage.setScene(scene);
        depositStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	private void showWithdrawDialog(int accountNumber) {
        Stage withdrawStage = new Stage();
        withdrawStage.setTitle("Withdraw");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 50, 50, 50));

        Label amountLabel = new Label("Enter amount you want to withdraw:");
        TextField amountField = new TextField();

        Button withdrawBtn = new Button("Withdraw");
        withdrawBtn.setOnAction(e -> {
            double withdrawalAmount = Double.parseDouble(amountField.getText());
            String message = atm.withdraw(accountNumber, withdrawalAmount);
            showAlert("Transaction Result", message);
            withdrawStage.close();
        });

        vbox.getChildren().addAll(amountLabel, amountField, withdrawBtn);

        Scene scene = new Scene(vbox, 300, 150);
        withdrawStage.setScene(scene);
        withdrawStage.show();
    }
	
	private void showCheckBalanceDialog(int accountNumber) {
	    Stage balanceStage = new Stage();
	    balanceStage.setTitle("Check Balance");

	    VBox vbox = new VBox(10);
	    vbox.setPadding(new Insets(20, 50, 50, 50));

	    Button checkBalanceBtn = new Button("Check Balance");
	    checkBalanceBtn.setOnAction(e -> {
	        String message = atm.checkBalance(accountNumber);
	        showAlert("Account Balance", message);
	        balanceStage.close();
	    });

	    vbox.getChildren().addAll(checkBalanceBtn);

	    Scene scene = new Scene(vbox, 300, 150);
	    balanceStage.setScene(scene);
	    balanceStage.show();
	}


}
