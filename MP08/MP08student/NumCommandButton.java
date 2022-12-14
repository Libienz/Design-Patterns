import javax.swing.*;

public class NumCommandButton extends JButton implements Command{
    Calculator calculator;
    JLabel display;

    public NumCommandButton(Calculator calculator, JLabel display) {
        this.calculator = calculator;
        this.display = display;
    }

    @Override
    public void execute() {

        if (calculator.isOperand1Set() && calculator.isOperatorSet()) { // 첫 번째 피연산자와 연산자가 지정되었다면 두 번째 피연산자 값으로 사용
            int num2 = Integer.parseInt(this.getText());
            calculator.setOperand2(num2);
            display.setText("" + num2);
            calculator.setOperand2Set(true);
        }
        else {  // 첫 번째 피연산자 값 지정
            int num1 = Integer.parseInt(this.getText());
            display.setText("" + num1);
            calculator.setOperand1(num1);
            calculator.setOperand1Set(true);
        }

    }
}
