package project.view;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import project.model.Algorithm;
import project.model.BisectionAlgorithm;
import project.model.MathFunction;
import project.model.MyPair;
import project.model.NewtonAlgorithm;


public class HelloController {

    @FXML
    private TextField precisionInput;
    @FXML
    private TextField iterationNumInput;
    @FXML
    private TextField startIntervalInput;
    @FXML
    private TextField endIntervalInput;
    @FXML
    private RadioButton precisionRadioButton;
    @FXML
    private RadioButton iterationRadioButton;
    @FXML
    private RadioButton bisectionRadioButton;
    @FXML
    private RadioButton mixed;
    @FXML
    private RadioButton exponential;
    @FXML
    private RadioButton polynomial;
    @FXML
    private RadioButton trigonometric;
    @FXML
    private RadioButton newtonRadioButton;
    @FXML
    private Label resultLabel;
    @FXML
    private LineChart<Double, Double> lineChart;
    @FXML
    private NumberAxis xAxis;
    String function;
    MathFunction mathFunction = new MathFunction();
    double precision;

    @FXML
    private void initialize() {
        restrictPrecisionInputFormat(precisionInput);
        restrictTextFieldInputToDouble(startIntervalInput);
        restrictTextFieldInputToDouble(endIntervalInput);
        restrictTextFieldInputToInt(iterationNumInput);
        iterationNumInput.setText(String.valueOf(1));
        lineChart.setAnimated(false);

    }

    @FXML
    protected void onGraphButtonPressed() {
        // trzeba tu jakos dostarczyc nasza funkcje, roboczo wpisana z palca nizej
        function = chooseFunctionByRadioButton();
        int resolution = 500;
        double firstPoint = Double.parseDouble(startIntervalInput.getText());
        double lastPoint = Double.parseDouble(endIntervalInput.getText());
        double[] x = new double[resolution];
        double[] y = new double[resolution];
        double xIncrement = (lastPoint - firstPoint) / resolution;
        double p = firstPoint;
        for (int i = 0; i < resolution; i++) {
            x[i] = p;
            // nasza funkcja f(x) ktora powinna przyjsc w funkcji
            switch (function) {
                case "polynomial" -> y[i] = mathFunction.polynomial(p);
                case "trigonometric" -> y[i] = mathFunction.trigonometric(p);
                case "exponential" -> y[i] = mathFunction.exponential(p);
                case "mixed" -> y[i] = mathFunction.mixed(p);
                default -> y[i] = 0;
            }
            p += xIncrement;
        }
        lineChart.getData().clear();
        Graph graph = new Graph(x, y);
        lineChart.getData().add(graph.createSeries());

        xAxis.setLowerBound(firstPoint);
        xAxis.setUpperBound(lastPoint);

    }

    @FXML
    protected void onPrecisionRadioButtonSelected() {
        precisionInput.setDisable(false);
        iterationNumInput.setDisable(true);
    }

    @FXML
    protected void onIterationRadioButtonSelected() {
        precisionInput.setDisable(true);
        iterationNumInput.setDisable(false);
        iterationNumInput.clear();
    }

    private String chooseFunctionByRadioButton() {
        if (polynomial.isSelected()) {
            return "polynomial";
        }
        if (trigonometric.isSelected()) {
            return "trigonometric";
        }
        if (exponential.isSelected()) {
            return "exponential";
        }
        if (mixed.isSelected()) {
            return "mixed";
        } else {
            return "";
        }

    }

    private void openWarningDialog(String text) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Błąd");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(text);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }

    @FXML
    protected void onCalculateButtonPressed() {
        Algorithm algorithm = null;
        precision = Double.parseDouble(precisionInput.getText());
        int iterationNum = Integer.parseInt(iterationNumInput.getText());
        double startInterval = Double.parseDouble(startIntervalInput.getText());
        double endInterval = Double.parseDouble(endIntervalInput.getText());
        String resultStr = "";
        if (bisectionRadioButton.isSelected()) {
            algorithm = new BisectionAlgorithm(precision, iterationNum, startInterval, endInterval,
                    chooseFunctionByRadioButton());
        } else if (newtonRadioButton.isSelected()) {
            algorithm = new NewtonAlgorithm(precision, iterationNum, startInterval, endInterval,
                    chooseFunctionByRadioButton());
        }
        if (!algorithm.isDifferentSignsValid()) {
            openWarningDialog("Znaki na brzegach przedziału muszą być przeciwne");
        } else if (!algorithm.isOneRootValid()) {
            openWarningDialog("Nie może być więcej niż jedno miejsce zerowe");
        }

        resultStr = algorithm(algorithm);
        resultLabel.setText("x0=" + resultStr);
    }

    private String algorithm(Algorithm algorithm) {
        MyPair<Double, Integer> result;
        double root = 0;
        String resultStr = "";
        if (precisionRadioButton.isSelected()) {
            if (precision == 0) {
                precisionInput.setStyle("-fx-control-inner-background: red;");
                return "";
            } else {
                precisionInput.setStyle("-fx-control-inner-background: white;");
                try {
                    result = algorithm.calculateWithPrecision();
                    root = result.first;
                    iterationNumInput.setText(String.valueOf(result.iterationCount));
                    int decimalPlacesNum = precisionInput.getText().length() - 2;
                    resultStr = String.format("%." + decimalPlacesNum + "f", root);
                } catch (ArithmeticException e) {
                    openWarningDialog("Pierwsza pochodna równa 0");
                }
            }

        } else if (iterationRadioButton.isSelected()) {
            try{
                root = algorithm.calculateWithIter();
                resultStr = String.format("%.6f", root);
            } catch (ArithmeticException e) {
                openWarningDialog("Pierwsza pochodna równa 0");
            }
        }
        return resultStr;
    }

    private void restrictPrecisionInputFormat(TextField field) {
        Pattern validEditingState = Pattern.compile("0?(\\.[0-9]*)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c;
            } else {
                return null;
            }
        };

        StringConverter<Double> converter = new StringConverter<>() {

            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || ".".equals(s)) {
                    return 0.0;
                } else {
                    return Double.valueOf(s);
                }
            }

            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };
        TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 0.0, filter);
        field.setTextFormatter(textFormatter);
    }

    private void restrictTextFieldInputToDouble(TextField field) {
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c;
            } else {
                return null;
            }
        };

        StringConverter<Double> converter = new StringConverter<>() {

            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0.0;
                } else {
                    return Double.valueOf(s);
                }
            }

            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };
        TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 0.0, filter);
        field.setTextFormatter(textFormatter);
    }

    private void restrictTextFieldInputToInt(TextField field) {
        Pattern validEditingState = Pattern.compile("(([1-9][0-9]*)|0)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c;
            } else {
                return null;
            }
        };

        StringConverter<Integer> converter = new StringConverter<>() {

            @Override
            public Integer fromString(String s) {
                if (s.isEmpty()) {
                    return 0;
                } else {
                    return Integer.valueOf(s);
                }
            }

            @Override
            public String toString(Integer d) {
                return d.toString();
            }
        };
        TextFormatter<Integer> textFormatter = new TextFormatter<>(converter, 0, filter);
        field.setTextFormatter(textFormatter);
    }

}