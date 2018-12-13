import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


public class UbankApp extends Application {
    Label amountLabel;
    FlowPane contentOfFoot2;
    static Label currentBalance;
    static Label helloUser;
    VBox vBox;
    private static Ubank ubank;
    @Override
    public void start(Stage primaryStage) throws Exception {
//Scene 1

        vBox = new VBox(10);
        Label label = new Label("Welcome to UBank");
        label.setFont(Font.font("Verdana",50));
        label.setTextFill(Color.WHITE);
        Label label2 = new Label("Don't have an account?");
        label2.setFont(Font.font("Times New Roman", FontPosture.ITALIC,18));
        label2.setTextFill(Color.WHITE);
        label.setAlignment(Pos.CENTER);
        Button createAccount = new Button("Create an account");
        Image image =new Image("images/logo.png") ;
        ImageView logo = new ImageView(image);
        createAccount.setStyle("-fx-background-color: #641927");
        createAccount.setTextFill(Color.WHITE);
        createAccount.setOnMouseEntered(event -> {
            createAccount.setStyle("-fx-background-color: #922138");
        });
        createAccount.setOnMouseExited(event -> {
            createAccount.setStyle("-fx-background-color: #641927");
        });
        logo.setFitHeight(80);
        logo.setFitWidth(80);


//Registration

        Label title = new Label("Create your UBank Account");
        title.setFont(Font.font("Verdana",30));
        title.setTextFill(Color.WHITE);
        HBox hBox = new HBox(50);
        Label username = new Label("Username: ");
        username.setTextFill(Color.WHITE);
        TextField userNameField = new TextField();
        userNameField.setMaxWidth(200);
        Label cardNumber = new Label("Card Number: ");
        cardNumber.setTextFill(Color.WHITE);
        TextField cardNumberField  = new TextField();
        cardNumberField.setMaxWidth(200);
        Label password = new Label("Password: ");
        password.setTextFill(Color.WHITE);
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(200);
        Button submitButton = new Button("Submit");

        VBox first_part = new VBox(10);
        first_part.getChildren().addAll(username,userNameField,cardNumber,cardNumberField,password,passwordField,submitButton);
        ImageView logo2 = new ImageView(image);
        logo2.setFitHeight(135);
        logo2.setFitWidth(135);
        Label desc = new Label(" Free | Comfortable | Safe  ");
        desc.setTextFill(Color.WHITE);
        VBox second_part = new VBox(5);
        second_part.setAlignment(Pos.CENTER);
        second_part.getChildren().addAll(logo2,desc);

        hBox.getChildren().addAll(first_part,second_part);
        hBox.setAlignment(Pos.CENTER);
        VBox vbox2 = new VBox(50);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.getChildren().addAll(title,hBox);
        vbox2.setStyle("-fx-background-color: #922138");
//Scene 3
        createAccount.setOnAction(e->{
           primaryStage.setScene(new Scene(vbox2,700,500));
        });

        Label message = new Label("");
        Label message2 =new Label("");
        Label message3 =new Label("");

        submitButton.setOnAction(event -> {
            message.setTextFill(Color.WHITE);
            if (userNameField.getText().length()==0) {
                message.setText("Username is required");
                message.setTextFill(Color.rgb(255, 255, 255));
            }
            else if(!userNameField.getText().equals(null)) {
                first_part.getChildren().remove(message);
            }
            if (cardNumberField.getText().length()<16 && cardNumberField.getText().length()!=0) {
                message2.setText("Card Number should be 16 length");
                message2.setTextFill(Color.rgb(255, 255, 255));
                cardNumberField.clear();
            }
            else if (cardNumberField.getText().length()==0) {
                message2.setText("Card Number is required");
                message2.setTextFill(Color.rgb(255, 255, 255));
                cardNumberField.clear();
            }
            else if (cardNumberField.getText().length()==16) {
                first_part.getChildren().remove(message2);
            }
            if (passwordField.getText().length()<7 && passwordField.getText().length()!=0 ) {
                message3.setText("Password should contain at least 7 symbols");
                message3.setTextFill(Color.rgb(255, 255, 255));
                passwordField.clear();
            }

            else if (passwordField.getText().length()==0 ) {
                message3.setText("Password is required");
                message3.setTextFill(Color.rgb(255, 255, 255));

            }

            else if (passwordField.getText().length()>=7) {
                first_part.getChildren().remove(message3);

            }
            if (!userNameField.getText().equals(null) && cardNumberField.getText().length()==16 && passwordField.getText().length()>=7 ){
                ubank = new Ubank(userNameField.getText(),cardNumberField.getText(),passwordField.getText());
                primaryStage.setScene(new Scene(homepage(primaryStage),700, 500));

            }
        });

        first_part.getChildren().addAll(message,message2,message3);

        vBox.setStyle("-fx-background-color: #922138");
        vBox.getChildren().addAll(logo,label,label2,createAccount);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox,700,500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public Pane homepage(Stage primaryStage) {
        helloUser = new Label("Hello, " + ubank.getUser().getUserName());
        helloUser.setFont(Font.font("Verdana",25));
        helloUser.setTextFill(Color.rgb(100,25,39));
        Label userCardNumber = new Label(""+ ubank.getUser().getCardNumber());
        userCardNumber.setFont(Font.font("Arial", 35));
        userCardNumber.setTextFill(Color.rgb(100,25,39));
        VBox topOfWindow = new VBox(25);
        topOfWindow.setPrefHeight(100);
        topOfWindow.setPadding(new Insets(20));
        topOfWindow.setStyle("-fx-background-color: #ffffff");

        topOfWindow.setPrefHeight(200);
        topOfWindow.getChildren().addAll(helloUser,userCardNumber);
        topOfWindow.setAlignment(Pos.BOTTOM_CENTER);
        Label balance = new Label("Current Balance: ");
        balance.setFont(Font.font(15));
        currentBalance = new Label("$" + ubank.getUser().getCurrentBalance());
        currentBalance.setFont(Font.font(30));
        FlowPane contentOfFoot1 = new FlowPane();
        contentOfFoot1.setHgap(20);
        contentOfFoot1.getChildren().addAll(balance,currentBalance);
        contentOfFoot1.setAlignment(Pos.TOP_LEFT);
        Label bonus = new Label("Bonuses: ");
        bonus.setFont(Font.font(15));
        Label currentBonus = new Label(""+ ubank.getUser().getBonus());
        currentBonus.setFont(Font.font(30));
        contentOfFoot2 = new FlowPane();
        contentOfFoot2.setHgap(20);
        contentOfFoot2.getChildren().addAll(bonus,currentBonus);
        contentOfFoot2.setAlignment(Pos.TOP_LEFT);

        VBox footOfWindow = new VBox();
        footOfWindow.getChildren().addAll(contentOfFoot1,contentOfFoot2);
        footOfWindow.setPadding(new Insets(10));
        footOfWindow.setPrefHeight(150);
        footOfWindow.setStyle("-fx-background-color: #ffffff");


        VBox contentOfPage = new VBox();
        contentOfPage.getChildren().addAll(topOfWindow,footOfWindow);
        contentOfPage.setPrefWidth(630);
        contentOfPage.setSpacing(30);
        contentOfPage.setPadding(new Insets(15));

//Side Menu
        VBox sideNenu = new VBox();
        sideNenu.setPrefWidth(70);
        sideNenu.setStyle("-fx-background-color: #922138");

        Button homeIcon = new Button();
        homeIcon.setPrefHeight(primaryStage.getHeight()/6);
        homeIcon.setPrefWidth(70);
        homeIcon.setGraphic(new ImageView(new Image("images/"+1 + ".png")));
        homeIcon.setStyle("-fx-background-color: #922138");
        decorator(homeIcon);

        homeIcon.setOnAction(event -> {
            primaryStage.setScene(new Scene(homepage(primaryStage),700,500));
        });

        Button paymentsIcon = new Button();
        paymentsIcon.setPrefHeight(primaryStage.getHeight()/6);
        paymentsIcon.setPrefWidth(70);
        paymentsIcon.setGraphic(new ImageView(new Image("images/"+2 + ".png")));
        paymentsIcon.setStyle("-fx-background-color: #922138");
        decorator(paymentsIcon);


        Button transactionIcon = new Button();
        transactionIcon.setPrefHeight(primaryStage.getHeight()/6);
        transactionIcon.setPrefWidth(70);
        transactionIcon.setGraphic(new ImageView(new Image("images/"+3+   ".png")));
        transactionIcon.setStyle("-fx-background-color: #922138");
        decorator(transactionIcon);

        transactionIcon.setOnAction(event -> {
            primaryStage.setScene(new Scene(transactionPage(primaryStage,sideNenu),700,500));
        });

        Button depCreIcon = new Button();
        depCreIcon.setPrefHeight(primaryStage.getHeight()/6);
        depCreIcon.setPrefWidth(70);
        depCreIcon.setGraphic(new ImageView(new Image("images/"+4 + ".png")));
        depCreIcon.setStyle("-fx-background-color: #922138");
        decorator(depCreIcon);

        depCreIcon.setOnAction(event -> {
            primaryStage.setScene(new Scene(depCrePage(primaryStage,sideNenu),700,500));
        });

        Button settingsIcon = new Button();
        settingsIcon.setPrefHeight(primaryStage.getHeight()/6);
        settingsIcon.setPrefWidth(70);
        settingsIcon.setGraphic(new ImageView(new Image("images/"+5 + ".png")));
        settingsIcon.setStyle("-fx-background-color: #922138");
        decorator(settingsIcon);

        settingsIcon.setOnAction(e->{
            primaryStage.setScene(new Scene(settingsPage(primaryStage,sideNenu),700,500));
        });

        Button aboutUsIcon = new Button();
        aboutUsIcon.setPrefHeight(primaryStage.getHeight()/6);
        aboutUsIcon.setPrefWidth(70);
        aboutUsIcon.setGraphic(new ImageView(new Image("images/"+6 + ".png")));
        aboutUsIcon.setStyle("-fx-background-color: #922138");
        decorator(aboutUsIcon);

        aboutUsIcon.setOnAction(e -> {
            primaryStage.setScene(new Scene(aboutUSPage(primaryStage,sideNenu),700,500));
        } );

        sideNenu.getChildren().addAll(homeIcon,paymentsIcon,transactionIcon,depCreIcon,settingsIcon,aboutUsIcon);

        paymentsIcon.setOnAction(event -> {
            primaryStage.setScene(new Scene(paymentPage(primaryStage,sideNenu),700,500));

        });

        HBox homePage = new HBox();
        homePage.getChildren().add(contentOfPage);
        homePage.getChildren().add(sideNenu);
        homePage.setStyle("-fx-background-color: #922138");

    return homePage;
    }
    private void decorator(Button btn){
        btn.setOnMouseEntered(event -> {
            btn.setStyle("-fx-background-color:#641927");
        });
        btn.setOnMouseExited(event -> {
            btn.setStyle("-fx-background-color: #922138");
        });
    }

    private Pane paymentPage(Stage primaryStage, Pane sideMenu) {
        VBox titleOnTop = new VBox();
        titleOnTop.setPadding(new Insets(15));
        Label titleLabel = new Label("Pay for Services");
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Verdana",20));
        titleLabel.setPadding(new Insets(10));
        FlowPane pane = new FlowPane();
        pane.setHgap(15);
        pane.setVgap(15);
        pane.setPrefWidth(630);
        pane.setPadding(new Insets(15));

        Button onayButton = new Button("Pay Onay");
        onayButton.setStyle("-fx-background-color: #ffffff");
        onayButton.setFont(Font.font("Verdana",15));
        onayButton.setPrefWidth(200);
        onayButton.setPrefHeight(70);
        Image onay = new Image("images/onayIcon.png");
        ImageView onayIcon = new ImageView(onay);
        onayIcon.setFitHeight(20);
        onayIcon.setFitWidth(20);
        onayButton.setGraphic(onayIcon);

        onayButton.setOnAction(event -> {
            primaryStage.setScene(new Scene(OnayScene(primaryStage,sideMenu),700,500));

        });

        Button phoneNumberButton = new Button("Pay Phone Number");
        phoneNumberButton.setStyle("-fx-background-color: #ffffff");
        phoneNumberButton.setFont(Font.font("Verdana",15));
        phoneNumberButton.setPrefWidth(200);
        phoneNumberButton.setPrefHeight(70);
        Image phoneNumber = new Image("images/phone.png");
        ImageView phoneNumberIcon = new ImageView(phoneNumber);
        phoneNumberIcon.setFitHeight(20);
        phoneNumberIcon.setFitWidth(20);
        phoneNumberButton.setGraphic(phoneNumberIcon);

        phoneNumberButton.setOnAction(event -> {
            primaryStage.setScene(new Scene(PhNuScene(primaryStage,sideMenu),700,500));
        });

        Button internetButton = new Button("Pay Internet");
        internetButton.setStyle("-fx-background-color: #ffffff");
        internetButton.setFont(Font.font("Verdana",15));
        internetButton.setPrefWidth(200);
        internetButton.setPrefHeight(70);
        Image internet = new Image("images/internet.png");
        ImageView internetIcon = new ImageView(internet);
        internetIcon.setFitHeight(20);
        internetIcon.setFitWidth(20);
        internetButton.setGraphic(internetIcon);

        internetButton.setOnAction(event -> {
            primaryStage.setScene(new Scene(intScene(primaryStage,sideMenu),700,500));
        });

        HBox paymentPage = new HBox();
        paymentPage.setStyle("-fx-background-color: #922138");
        pane.getChildren().addAll(onayButton,phoneNumberButton,internetButton);
        titleOnTop.getChildren().addAll(titleLabel,pane);
        paymentPage.getChildren().addAll(titleOnTop,sideMenu);

        return paymentPage;

    }

    public Pane transactionPage(Stage primaryStage, Pane sideMenu) {
        VBox pane = new VBox(15);
        pane.setPadding(new Insets(30));
        pane.setPrefWidth(630);
        pane.setStyle("-fx-background-color: #922138");
        Label titleLabel = new Label("Send your money to Someone");
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Verdana",20));

        Button makeTransactionButton = new Button("Make Transaction ");
        makeTransactionButton.setStyle("-fx-background-color: #ffffff");
        makeTransactionButton.setFont(Font.font("Verdana",15));
        makeTransactionButton.setPrefWidth(200);
        makeTransactionButton.setPrefHeight(70);
        makeTransactionButton.setOnAction(event -> {
            primaryStage.setScene(new Scene(transactionScene(primaryStage,sideMenu),700,500));

        });

        pane.getChildren().addAll(titleLabel,makeTransactionButton);
        HBox total = new HBox();
        total.getChildren().addAll(pane,sideMenu);

        return total;

    }

    public Pane settingsPage(Stage primaryStage, Pane sideMenu){
        HBox final_view = new HBox();
        final_view.setStyle("-fx-background-color: #922138");
        VBox titleOnTop = new VBox();
        titleOnTop.setPadding(new Insets(15));
        Label titleLabel = new Label("Settings");
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Verdana",20));
        titleLabel.setPadding(new Insets(10));
        FlowPane options = new FlowPane();
        options.setHgap(15);
        options.setVgap(15);
        options.setPrefWidth(630);
        options.setPadding(new Insets(15));

        Button chUsButton = new Button("Change Username");
        chUsButton.setStyle("-fx-background-color: #ffffff");
        chUsButton.setFont(Font.font("Verdana",15));
        chUsButton.setPrefWidth(200);
        chUsButton.setPrefHeight(70);
        Image user = new Image("images/user.png");
        ImageView userIcon = new ImageView(user);
        userIcon.setFitHeight(20);
        userIcon.setFitWidth(20);
        chUsButton.setGraphic(userIcon);

        chUsButton.setOnAction(e->{
            primaryStage.setScene(new Scene(changeUserName(sideMenu),700,500));

        });

        Button chPassButton = new Button("Change Password");
        chPassButton.setStyle("-fx-background-color: #ffffff");
        chPassButton.setFont(Font.font("Verdana",15));
        chPassButton.setPrefWidth(200);
        chPassButton.setPrefHeight(70);
        Image password = new Image("images/password.png");
        ImageView passwordIcon = new ImageView(password);
        passwordIcon.setFitHeight(20);
        passwordIcon.setFitWidth(20);
        chPassButton.setGraphic(passwordIcon);

        chPassButton.setOnAction(event -> {
            primaryStage.setScene(new Scene(changePassword(sideMenu),700,500));
        });

        Button logOutButton = new Button("Log Out");
        logOutButton.setStyle("-fx-background-color: #ffffff");
        logOutButton.setFont(Font.font("Verdana",15));
        logOutButton.setPrefWidth(200);
        logOutButton.setPrefHeight(70);
        Image log_out = new Image("images/logout.png");
        ImageView logoutIcon = new ImageView(log_out);
        logoutIcon.setFitHeight(20);
        logoutIcon.setFitWidth(20);
        logOutButton.setGraphic(logoutIcon);

        logOutButton.setOnAction(event -> {
            try {
                start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        options.getChildren().addAll(chUsButton,chPassButton,logOutButton);
        titleOnTop.getChildren().addAll(titleLabel,options);
        final_view.getChildren().addAll(titleOnTop,sideMenu);

        return final_view;
    }

    public Pane depCrePage(Stage primaryStage, Pane sideMenu) {
        HBox final_view = new HBox();
        final_view.setStyle("-fx-background-color: #922138");
        VBox titleOnTop = new VBox();
        titleOnTop.setPadding(new Insets(15));
        Label titleLabel = new Label("Deposit/Credit");
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Verdana",20));
        titleLabel.setPadding(new Insets(10));
        FlowPane options = new FlowPane();
        options.setHgap(15);
        options.setVgap(15);
        options.setPrefWidth(630);
        options.setPadding(new Insets(15));

        Button create_deposit = new Button("Create Deposit");
        create_deposit.setStyle("-fx-background-color: #ffffff");
        create_deposit.setFont(Font.font("Verdana",15));
        create_deposit.setPrefWidth(200);
        create_deposit.setPrefHeight(70);
        Image deposit = new Image("images/deposit.png");
        ImageView depositIcon = new ImageView(deposit);
        depositIcon.setFitHeight(20);
        depositIcon.setFitWidth(20);
        create_deposit.setGraphic(depositIcon);

        create_deposit.setOnAction(event -> {
            primaryStage.setScene(new Scene(createDeposit(sideMenu),700,500));
        });

        Button register_credit = new Button("Register Credit");
        create_deposit.setStyle("-fx-background-color: #ffffff");
        register_credit.setFont(Font.font("Verdana",15));
        register_credit.setPrefWidth(200);
        register_credit.setPrefHeight(70);
        Image credit = new Image("images/crediting.png");
        ImageView creditIcon = new ImageView(credit);
        creditIcon.setFitHeight(20);
        creditIcon.setFitWidth(20);
        register_credit.setGraphic(creditIcon);

        register_credit.setOnAction(event -> {
            primaryStage.setScene(new Scene(registerCredit(sideMenu),700,500));
        });

        options.getChildren().addAll(create_deposit,register_credit);
        titleOnTop.getChildren().addAll(titleLabel,options);
        final_view.getChildren().addAll(titleOnTop,sideMenu);

        return final_view;



    }

    public Pane OnayScene(Stage primaryStage, Pane sideMenu) {
        VBox final_form = new VBox(20);
        VBox number_asking = new VBox();
        number_asking.setPrefHeight(80);
        number_asking.setMaxWidth(450);
        number_asking.setStyle("-fx-background-color: #ffffff");
        Label askForOnayNum = new Label("Onay Card number: ");
        askForOnayNum.setFont(Font.font("Verdana",20));
        HBox enteringNumber = new HBox();
        enteringNumber.setSpacing(10);
        Label fixedNumber = new Label("9643 10 85033");
        TextField onayNumber = new TextField();
        onayNumber.setPrefWidth(150);
        enteringNumber.getChildren().addAll(fixedNumber,onayNumber);
        number_asking.setPadding(new Insets(12));
        number_asking.getChildren().addAll(askForOnayNum,enteringNumber);


        HBox amount_asking = new HBox();
        amount_asking.setAlignment(Pos.CENTER_LEFT);
        amount_asking.setSpacing(10);
        amount_asking.setMaxWidth(450);
        amount_asking.setPadding(new Insets(12));
        Label askForAmount = new Label("$");
        askForAmount.setFont(Font.font("verdana",20));
        TextField amount = new TextField();
        amount.setPrefWidth(200);
        amount_asking.getChildren().addAll(askForAmount,amount);
        amount_asking.setPrefHeight(100);
        amount_asking.setStyle("-fx-background-color: #ffffff");

        Button pay = new Button("Pay");
        pay.setPrefWidth(80);

        final_form.getChildren().addAll(number_asking,amount_asking,pay);
        final_form.setSpacing(20);
        final_form.setStyle("-fx-background-color: #922138");
        final_form.setPrefWidth(630);
        final_form.setPadding(new Insets(20));
        pay.setOnAction(event1 -> {
            primaryStage.setScene(new Scene(verifyPaymentOnay(primaryStage,fixedNumber,onayNumber,amount,sideMenu),700,500));
        });
        HBox page = new HBox();
        page.getChildren().addAll(final_form,sideMenu);

        return page;


    }

    public Pane PhNuScene(Stage primaryStage,Pane sideMenu) {
        VBox final_form = new VBox(20);
        VBox number_asking = new VBox();
        number_asking.setPrefHeight(80);
        number_asking.setMaxWidth(450);
        number_asking.setStyle("-fx-background-color: #ffffff");
        Label askForPhNum = new Label("Phone Number: ");
        askForPhNum.setFont(Font.font("Verdana", 20));
        HBox enteringNumber = new HBox();
        enteringNumber.setSpacing(10);
        Label fixedNumber = new Label("+7");
        TextField phNumber = new TextField();
        phNumber.setPrefWidth(200);
        enteringNumber.getChildren().addAll(fixedNumber, phNumber);
        number_asking.setPadding(new Insets(12));
        number_asking.getChildren().addAll(askForPhNum, enteringNumber);


        HBox amount_asking = new HBox();
        amount_asking.setAlignment(Pos.CENTER_LEFT);
        amount_asking.setSpacing(10);
        amount_asking.setMaxWidth(450);
        amount_asking.setPadding(new Insets(12));
        Label askForAmount = new Label("$");
        askForAmount.setFont(Font.font("Verdana", 20));
        TextField amount = new TextField();
        amount.setPrefWidth(200);
        amount_asking.getChildren().addAll(askForAmount, amount);
        amount_asking.setPrefHeight(100);
        amount_asking.setStyle("-fx-background-color: #ffffff");

        Button pay = new Button("Pay");
        pay.setPrefWidth(80);

        final_form.getChildren().addAll(number_asking, amount_asking, pay);
        final_form.setSpacing(20);
        final_form.setStyle("-fx-background-color: #922138");
        final_form.setPrefWidth(630);
        final_form.setPadding(new Insets(20));

        pay.setOnAction(event1 -> {
            primaryStage.setScene(new Scene(verifyPaymentPhNum(primaryStage,phNumber,amount,sideMenu)));
      });

        HBox page = new HBox();
        page.getChildren().addAll(final_form, sideMenu);

        return page;
    }

    public Pane intScene(Stage primaryStage,Pane sideMenu){
        VBox final_form = new VBox(20);
        VBox number_asking = new VBox();
        number_asking.setPrefHeight(80);
        number_asking.setMaxWidth(450);
        number_asking.setStyle("-fx-background-color: #ffffff");
        Label askForIntNum = new Label("Internet Number: ");
        askForIntNum.setFont(Font.font("Verdana",20));
        TextField IntNumber = new TextField();
        IntNumber.setPrefWidth(150);
        number_asking.setPadding(new Insets(12));
        number_asking.getChildren().addAll(askForIntNum,IntNumber);


        HBox amount_asking = new HBox();
        amount_asking.setAlignment(Pos.CENTER_LEFT);
        amount_asking.setSpacing(10);
        amount_asking.setMaxWidth(450);
        amount_asking.setPadding(new Insets(12));
        Label askForAmount = new Label("$");
        askForAmount.setFont(Font.font("Verdana",20));
        TextField amount = new TextField();
        amount.setPrefWidth(200);
        amount_asking.getChildren().addAll(askForAmount,amount);
        amount_asking.setPrefHeight(100);
        amount_asking.setStyle("-fx-background-color: #ffffff");

        Button pay = new Button("Pay");
        pay.setPrefWidth(80);

        final_form.getChildren().addAll(number_asking,amount_asking,pay);
        final_form.setSpacing(20);
        final_form.setStyle("-fx-background-color: #922138");
        final_form.setPrefWidth(630);
        final_form.setPadding(new Insets(20));

        pay.setOnAction(event1 -> {
            primaryStage.setScene(new Scene(verifyPaymentInternet(primaryStage,IntNumber,amount,sideMenu),700,500));
        });

        HBox page = new HBox();
        page.getChildren().addAll(final_form,sideMenu);

        return page;
    }

    public Pane verifyPaymentInternet(Stage primaryStage,TextField number,TextField amount, Pane sideMenu){
        VBox form_pass = new VBox(10);
        VBox form_body = new VBox(10);
        form_body.setStyle("-fx-background-color: #ffffff");
        form_body.setPadding(new Insets(15));

        Label label = new Label("Enter your Password:");
        PasswordField password = new PasswordField();

        form_body.getChildren().addAll(label,password);
        Button pay = new Button("Pay");
        form_pass.getChildren().addAll(form_body,pay);
        pay.setPrefWidth(80);
        form_pass.setPrefWidth(630);
        form_pass.setStyle("-fx-background-color: #922138");
        HBox total = new HBox();
        total.getChildren().addAll(form_pass,sideMenu);


        pay.setOnAction(event -> {
            if (ubank.verify(password.getText())){
            Services services = new Services(ubank.getUser().getUserName(),ubank.getUser().getCardNumber(),ubank.getUser().getPassword());
            String card_number = number.getText();
            String result = services.payInternet(card_number,amount.getText());
            Label answer = new Label(result);
            answer.setFont(Font.font("Verdana",30));
            answer.setTextFill(Color.WHITE);

            VBox centered_ann = new VBox(20);
            Button backButton;
            if (result.equals("Payment has been Successful!")){
                currentBalance.setText(""+ubank.getUser().getCurrentBalance());
                backButton = new Button("Back to Payments");
                backButton.setOnAction(e -> {
                    primaryStage.setScene(new Scene(paymentPage(primaryStage,sideMenu),700,500));
                });


            }
            else if (result.equals("You don't have enough money!")){
                backButton = new Button("Back to Payments");
                backButton.setOnAction(e -> {
                    primaryStage.setScene(new Scene(paymentPage(primaryStage,sideMenu),700,500));
                });


            }else {
                backButton = new Button("Try again");
                backButton.setOnAction(e ->{
                    primaryStage.setScene(new Scene(PhNuScene(primaryStage,sideMenu),700,500));
                });

            }
            centered_ann.getChildren().addAll(answer,backButton);
            centered_ann.setAlignment(Pos.CENTER);
            centered_ann.setStyle("-fx-background-color: #922138");
            centered_ann.setPrefWidth(630);
            HBox basic = new HBox();
            basic.getChildren().addAll(centered_ann,sideMenu);
            primaryStage.setScene(new Scene(basic,700,500));

        }
            else if (ubank.verify(password.getText())!=true){
                Label label1 = new Label("Incorrect Password!");
                label1.setTextFill(Color.WHITE);
                password.clear();
                form_pass.getChildren().add(label1);
            }

    });

        return total;
    }

    public Pane verifyPaymentPhNum(Stage primaryStage,TextField number,TextField amount, Pane sideMenu){
        VBox form_pass = new VBox(10);
        VBox form_body = new VBox(10);
        form_body.setStyle("-fx-background-color: #ffffff");
        form_body.setPadding(new Insets(15));

        Label label = new Label("Enter your Password:");
        PasswordField password = new PasswordField();

        form_body.getChildren().addAll(label,password);
        Button pay = new Button("Pay");
        form_pass.getChildren().addAll(form_body,pay);
        pay.setPrefWidth(80);
        form_pass.setPrefWidth(630);
        form_pass.setStyle("-fx-background-color: #922138");
        HBox total = new HBox();
        total.getChildren().addAll(form_pass,sideMenu);


        pay.setOnAction(event -> {
            if(ubank.verify(password.getText())) {
                Services services = new Services(ubank.getUser().getUserName(), ubank.getUser().getCardNumber(), ubank.getUser().getPassword());
                String phone_number = number.getText();
                String result = services.payPhoneNumber(phone_number, amount.getText());
                Label answer = new Label(result);
                answer.setFont(Font.font("Verdana", 30));
                answer.setTextFill(Color.WHITE);

                VBox centered_ann = new VBox(20);
                Button backButton;
                if (result.equals("Payment has been Succesfully!")) {
                    currentBalance.setText(""+ubank.getUser().getCurrentBalance());
                    backButton = new Button("Back to Payments");
                    backButton.setOnAction(e -> {
                        primaryStage.setScene(new Scene(paymentPage(primaryStage, sideMenu), 700, 500));
                    });


                } else if (result.equals("You don't have enough money!")) {
                    backButton = new Button("Back to Payments");
                    backButton.setOnAction(e -> {
                        primaryStage.setScene(new Scene(paymentPage(primaryStage, sideMenu), 700, 500));
                    });


                } else {
                    backButton = new Button("Try again");
                    backButton.setOnAction(e -> {
                        primaryStage.setScene(new Scene(PhNuScene(primaryStage, sideMenu), 700, 500));
                    });

                }
                centered_ann.getChildren().addAll(answer, backButton);
                centered_ann.setAlignment(Pos.CENTER);
                centered_ann.setStyle("-fx-background-color: #922138");
                centered_ann.setPrefWidth(630);
                HBox basic = new HBox();
                basic.getChildren().addAll(centered_ann, sideMenu);
                primaryStage.setScene(new Scene(basic, 700, 500));
            }
            else if (ubank.verify(password.getText())!=true){
                Label label1 = new Label("Incorrect Password!");
                label1.setTextFill(Color.WHITE);
                password.clear();
                form_pass.getChildren().add(label1);
            }

            });

        return total;

    }

    public Pane verifyPaymentOnay(Stage primaryStage, Label fixedNumber,TextField onayNumber,TextField amount, Pane sideMenu){
        VBox form_pass = new VBox(10);
        VBox form_body = new VBox(10);
        form_body.setStyle("-fx-background-color: #ffffff");
        form_body.setPadding(new Insets(15));

        Label label = new Label("Enter your Password:");
        PasswordField password = new PasswordField();

        form_body.getChildren().addAll(label,password);
        Button pay = new Button("Pay");
        form_pass.getChildren().addAll(form_body,pay);
        pay.setPrefWidth(80);
        form_pass.setPrefWidth(630);
        form_pass.setStyle("-fx-background-color: #922138");
        HBox total = new HBox();
        total.getChildren().addAll(form_pass,sideMenu);

        pay.setOnAction(event -> {
            if (ubank.verify(password.getText())){
            Services services = new Services(ubank.getUser().getUserName(),ubank.getUser().getCardNumber(),ubank.getUser().getPassword());
            String total_onay_number = fixedNumber.getText() + onayNumber.getText();
            String result = services.payOnay(total_onay_number,amount.getText());
            Label answer = new Label(result);
            answer.setFont(Font.font("Verdana",30));
            answer.setTextFill(Color.WHITE);

            VBox centered_ann = new VBox(20);
            Button backButton;
            if (result.equals("Payment has been Succesfully!")){
                currentBalance.setText(""+ubank.getUser().getCurrentBalance());

                notifyUser(amount.getText());
                backButton = new Button("Back to Payments");
                backButton.setOnAction(e -> {
                    primaryStage.setScene(new Scene(paymentPage(primaryStage,sideMenu),700,500));
                });
            }
            else if (result.equals("You don't have enough money!")){
                backButton = new Button("Back to Payments");
                backButton.setOnAction(e -> {
                    primaryStage.setScene(new Scene(paymentPage(primaryStage,sideMenu),700,500));
                });


            }else {
                backButton = new Button("Try again");
                backButton.setOnAction(e -> {
                    primaryStage.setScene(new Scene(OnayScene(primaryStage,sideMenu),700,500));
                });

            }

            centered_ann.getChildren().addAll(answer,backButton);
            centered_ann.setAlignment(Pos.CENTER);
            centered_ann.setStyle("-fx-background-color: #922138");
            centered_ann.setPrefWidth(630);
            HBox basic = new HBox();
            basic.getChildren().addAll(centered_ann,sideMenu);
            primaryStage.setScene(new Scene(basic,700,500));}

            else if (ubank.verify(password.getText())!=true){
                Label label1 = new Label("Incorrect Password!");
                label1.setTextFill(Color.WHITE);
                password.clear();
                form_pass.getChildren().add(label1);
            }


            });

        return total;
    }

    public Pane transactionScene(Stage primaryStage, Pane sideMenu) {
        VBox final_form = new VBox(20);
        VBox number_asking = new VBox();
        number_asking.setPrefHeight(80);
        number_asking.setMaxWidth(450);
        number_asking.setStyle("-fx-background-color: #ffffff");
        Label askForNum = new Label("Card Number: ");
        askForNum.setFont(Font.font("Verdana", 20));
        TextField cardNumber = new TextField();
        cardNumber.setPrefWidth(200);
        number_asking.setPadding(new Insets(12));
        number_asking.getChildren().addAll(askForNum,cardNumber);

        HBox amount_asking = new HBox();
        amount_asking.setAlignment(Pos.CENTER_LEFT);
        amount_asking.setSpacing(10);
        amount_asking.setMaxWidth(450);
        amount_asking.setPadding(new Insets(12));
        Label askForAmount = new Label("$");
        askForAmount.setFont(Font.font("Verdana", 20));
        TextField amount = new TextField();
        amount.setPrefWidth(200);
        amount_asking.getChildren().addAll(askForAmount, amount);
        amount_asking.setPrefHeight(100);
        amount_asking.setStyle("-fx-background-color: #ffffff");

        Button transfer = new Button("Transfer");
        transfer.setPrefWidth(80);

        final_form.getChildren().addAll(number_asking, amount_asking, transfer);
        final_form.setSpacing(20);
        final_form.setStyle("-fx-background-color: #922138");
        final_form.setPrefWidth(630);
        final_form.setPadding(new Insets(20));

        transfer.setOnAction(event1 -> {
            primaryStage.setScene(new Scene(verifyTransaction(primaryStage,cardNumber,amount,sideMenu)));
        });

        HBox page = new HBox();
        page.getChildren().addAll(final_form, sideMenu);

        return page;
    }

    public Pane verifyTransaction(Stage primaryStage,TextField cardNumber,TextField amount,Pane sideMenu) {
        VBox form_pass = new VBox(10);
        VBox form_body = new VBox(10);
        form_body.setStyle("-fx-background-color: #ffffff");
        form_body.setPadding(new Insets(15));

        Label label = new Label("Enter your Password:");
        PasswordField password = new PasswordField();

        form_body.getChildren().addAll(label,password);
        Button transfer = new Button("Transfer");
        form_pass.getChildren().addAll(form_body,transfer);
        transfer.setPrefWidth(80);
        form_pass.setPrefWidth(630);
        form_pass.setStyle("-fx-background-color: #922138");
        HBox total = new HBox();
        total.getChildren().addAll(form_pass,sideMenu);


        transfer.setOnAction(event -> {
            if(ubank.verify(password.getText())) {
                String card_number = cardNumber.getText();
                String result = ubank.makeTransactions(card_number, amount.getText());
                Label answer = new Label(result);
                answer.setFont(Font.font("Verdana", 30));
                answer.setTextFill(Color.WHITE);

                VBox centered_ann = new VBox(20);
                Button backButton;
                if (result.equals("Transaction has been Succesfully!")) {
                    currentBalance.setText(""+ubank.getUser().getCurrentBalance());
                    backButton = new Button("Back to Home");
                    backButton.setOnAction(e -> {
                        primaryStage.setScene(new Scene(homepage(primaryStage), 700, 500));
                    });


                } else if (result.equals("You don't have enough money!")) {
                    backButton = new Button("Back to Transaction");
                    backButton.setOnAction(e -> {
                        primaryStage.setScene(new Scene(transactionScene(primaryStage, sideMenu), 700, 500));
                    });


                } else {
                    backButton = new Button("Try again");
                    backButton.setOnAction(e -> {
                        primaryStage.setScene(new Scene(transactionScene(primaryStage, sideMenu), 700, 500));
                    });

                }
                centered_ann.getChildren().addAll(answer, backButton);
                centered_ann.setAlignment(Pos.CENTER);
                centered_ann.setStyle("-fx-background-color: #922138");
                centered_ann.setPrefWidth(630);
                HBox basic = new HBox();
                basic.getChildren().addAll(centered_ann, sideMenu);
                primaryStage.setScene(new Scene(basic, 700, 500));
            }
            else if (ubank.verify(password.getText())!=true){
                Label label1 = new Label("Incorrect Password!");
                label1.setTextFill(Color.WHITE);
                password.clear();
                form_pass.getChildren().add(label1);
            }

        });

        return total;
    }

    public Pane aboutUSPage(Stage primaryStage, Pane sideMenu){
        HBox final_page = new HBox();
        final_page.setStyle("-fx-background-color: #922138");
        final_page.setPadding(new Insets(15));
        VBox main_body = new VBox(10);
        main_body.setPrefWidth(630);
        Label description = new Label();
        Label title = new Label("About Us");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Verdana",20));
        description.setText("U Banking Group is licensed as an Islamic\n" +
                "wholesale bank by the Central Bank of Bahrain and is listed\n" +
                "on Bahrain Bourse and Nasdaq Dubai stock exchanges. \n" +
                "It is a leading international Islamic banking group providing\n" +
                "its unique services in countries with a population totaling\n" +
                "around one billion. It is rated A+ (bh) (long term) / A2 (bh)\n" +
                "on the national scale by Islamic International Rating Agency \n" +
                "and by Standard & Poor's at BB / B (short term).");
        description.setStyle("-fx-background-color: #ffffff");
        description.setFont(Font.font("Verdana",17));
        description.setPadding(new Insets(5,0,5,15));
        description.setPrefHeight(250);
        description.setPrefWidth(590);

        Label contact_us = new Label("Call Center 7777\n" +
                "@Copyright 2018 U Company");
        contact_us.setFont(Font.font("Verdana",15));

        contact_us.setStyle("-fx-background-color: #ffffff");
        contact_us.setPadding(new Insets(10));
        contact_us.setPrefHeight(70);
        contact_us.setPrefWidth(590);
        main_body.getChildren().addAll(title,description,contact_us);

        final_page.getChildren().addAll(main_body,sideMenu);

        return final_page;

    }

    public Pane changeUserName(Pane sideMenu){
        VBox ask_userName = new VBox(10);
        ask_userName.setPadding(new Insets(15));
        Label titleLabel = new Label("Change Username");
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Verdana",20));
        titleLabel.setPadding(new Insets(10));
        VBox form_body = new VBox(10);
        form_body.setStyle("-fx-background-color: #ffffff");
        form_body.setPadding(new Insets(15));

        Label label = new Label("New Username:");
        TextField new_username = new TextField();
        Label changing_status = new Label("Done!");
        changing_status.setTextFill(Color.WHITE);

        form_body.getChildren().addAll(label,new_username);
        Button change = new Button("Change");
        ask_userName.getChildren().addAll(titleLabel,form_body,change);
        change.setPrefWidth(80);
        change.setOnAction(e->{
            ubank.getUser().setUserName(new_username.getText());
            ask_userName.getChildren().add(changing_status);
            new_username.clear();
        });

        ask_userName.setPrefWidth(630);
        ask_userName.setStyle("-fx-background-color: #922138");
        HBox total = new HBox();
        total.getChildren().addAll(ask_userName,sideMenu);

        return total;
    }

    public Pane changePassword(Pane sideMenu){
        VBox ask_password = new VBox(10);
        ask_password.setPadding(new Insets(15));
        Label titleLabel = new Label("Change Password");
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Verdana",20));
        titleLabel.setPadding(new Insets(10));
        VBox form_body = new VBox(10);
        form_body.setStyle("-fx-background-color: #ffffff");
        form_body.setPadding(new Insets(15));

        Label label = new Label("Old Password:");
        PasswordField old_password = new PasswordField();

        Label label2 = new Label("New Password:");
        PasswordField new_password = new PasswordField();
        Label changing_status = new Label("Done!");
        changing_status.setTextFill(Color.WHITE);

        form_body.getChildren().addAll(label,old_password,label2,new_password);
        Button change = new Button("Change");
        ask_password.getChildren().addAll(titleLabel,form_body,change);
        change.setPrefWidth(80);
        Label ann = new Label("Incorrect Old Password!");
        ann.setTextFill(Color.WHITE);
        change.setOnAction(e->{
            if(old_password.getText().equals(ubank.getUser().getPassword())){
                ubank.getUser().setPassword(new_password.getText());
                ask_password.getChildren().remove(ann);
                ask_password.getChildren().add(changing_status);
                old_password.clear();
                new_password.clear();
            }else if (!old_password.getText().equals(ubank.getUser().getPassword())){
                ask_password.getChildren().add(ann);
            }
        });

        ask_password.setPrefWidth(630);
        ask_password.setStyle("-fx-background-color: #922138");
        HBox total = new HBox();
        total.getChildren().addAll(ask_password,sideMenu);

        return total;
    }

    public Pane createDeposit(Pane sideMenu){
        VBox creating_dep = new VBox(10);
        creating_dep.setPadding(new Insets(15));
        Label titleLabel = new Label("Create Deposit");
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Verdana",20));
        titleLabel.setPadding(new Insets(10));
        VBox form_body = new VBox(10);
        form_body.setStyle("-fx-background-color: #ffffff");
        form_body.setPadding(new Insets(15));

        Label label = new Label("Years for Deposit:");
        TextField years = new TextField();

        amountLabel = new Label("Amount");
        TextField amount = new TextField();

        Label changing_status = new Label("Done!");
        changing_status.setTextFill(Color.WHITE);


        Label check_for_error = new Label("You don't have enough money!");
        check_for_error.setTextFill(Color.WHITE);


        form_body.getChildren().addAll(label,years,amountLabel,amount);
        Button create = new Button("Create");
        create.setOnAction(event -> {
            if (ubank.getUser().getCurrentBalance()!=0 && Integer.parseInt(amount.getText())<=ubank.getUser().getCurrentBalance()){
                ubank.getUser().setCurrentBalance(ubank.getUser().getCurrentBalance()-Integer.parseInt(amount.getText()));
                Label ann = new Label("Your Deposit: " + amount.getText());
                ann.setStyle("-fx-background-color: #ffffff");
                ann.setPrefWidth(300);
                ann.setPadding(new Insets(10));
                ann.setFont(Font.font("Verdana",10));
                creating_dep.getChildren().addAll(changing_status,ann);
                amount.clear();
                years.clear();

            }
            else if (ubank.getUser().getCurrentBalance()==0 || Integer.parseInt(amount.getText())>ubank.getUser().getCurrentBalance()){
                years.clear();
                amount.clear();
                creating_dep.getChildren().add(check_for_error);
            }


        });
        create.setPrefWidth(80);
        creating_dep.getChildren().addAll(titleLabel,form_body,create);


        creating_dep.setPrefWidth(630);
        creating_dep.setStyle("-fx-background-color: #922138");
        HBox total = new HBox();
        total.getChildren().addAll(creating_dep,sideMenu);

        return total;
    }

    public Pane registerCredit(Pane sideMenu){
        VBox register_credit = new VBox(10);
        register_credit.setPadding(new Insets(15));
        Label titleLabel = new Label("Register Credit");
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Verdana",20));
        titleLabel.setPadding(new Insets(10));
        VBox form_body = new VBox(10);
        form_body.setStyle("-fx-background-color: #ffffff");
        form_body.setPadding(new Insets(15));

        Label firstNameLabel = new Label("First Name:");
        TextField firstName = new TextField();

        Label lastNameLabel = new Label("Last Name:");
        TextField lastName = new TextField();

        Label amountLabel = new Label("Credit Amount (the rate is 10.5%) :");
        TextField amount = new TextField();

        Label durationLabel = new Label("Amount of months( not less than 12):");
        TextField months = new TextField();

        Label changing_status = new Label("Done!");
        changing_status.setTextFill(Color.WHITE);


        form_body.getChildren().addAll(firstNameLabel,firstName,lastNameLabel,lastName,amountLabel,amount,durationLabel,months);
        Button create = new Button("Create");
        create.setOnAction(event -> {
            int amountOfCredits = Integer.parseInt(amount.getText());
            int amountOfMonths = Integer.parseInt(months.getText());
            Label credit_info = new Label("Dear "+firstName.getText() + " "+ lastName.getText() +
                    " you have registered credit for " +amount.getText()+" for "+months.getText()+"months\n"+
                    "The interest rate is 10.5%\n" +
                    "Your month payment is: " + ubank.creditAmount(amountOfCredits,amountOfMonths));
            credit_info.setFont(Font.font("Verdana",12));
            credit_info.setPadding(new Insets(10));
            credit_info.setStyle("-fx-background-color: #ffffff");

            firstName.clear();
            lastName.clear();
            amount.clear();
            months.clear();
            register_credit.getChildren().add(changing_status);
            register_credit.getChildren().add(credit_info);
        });
        create.setPrefWidth(80);
        register_credit.getChildren().addAll(titleLabel,form_body,create);


        register_credit.setPrefWidth(630);
        register_credit.setStyle("-fx-background-color: #922138");
        HBox total = new HBox();
        total.getChildren().addAll(register_credit,sideMenu);

        return total;

    }

    public void notifyUser(String amount) {
        Image image = new Image("iamges/checked.png");

        Notifications notificationBuilder = Notifications.create()
                .title("UBank App")
                .text(ubank.notification.notifyObserver(amount))
                .graphic(new ImageView(image))
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.showConfirm();

        AudioClip mediaPlayer = new AudioClip(this.getClass().getResource("316908__jaz-the-man-2__re.wav").toString());
        mediaPlayer.play();
    }

}
