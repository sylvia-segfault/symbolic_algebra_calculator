package calculator.ast.operators;

import calculator.ast.AstNode;
import calculator.errors.EvaluationError;
import calculator.gui.ImageDrawer;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;

public class GuiOperators {
    /**
     * This function is responsible for handling the `clear()` operation node.
     *
     * It clears the plotting window when invoked.
     */
    public static AstNode handleClear(AstNode wrapper, IDictionary<String, AstNode> variables, ImageDrawer drawer) {
        AstNode.assertOperatorValid("clear", wrapper);

        drawer.getGraphics().clearRect(0, 0, drawer.getWidth(), drawer.getHeight());

        return wrapper;
    }

    /**
     * Takes as input a 'plot(exprToPlot, var, varMin, varMax, step)' AstNode,
     * the dictionary of variables, and an ImageDrawer, and generates the
     * corresponding plot on the ImageDrawer. Returns some arbitrary AstNode.
     *
     * There are no other side effects for the inputs.
     *
     * Example 1:
     *
     * >>> plot(3 * x, x, 2, 5, 0.5)
     *
     * This method will receive the AstNode corresponding to 'plot(3 * x, x, 2, 5, 0.5)'.
     * Your 'handlePlot' method is then responsible for plotting the equation
     * "3 * x", varying "x" from 2 to 5 in increments of 0.5.
     *
     * In this case, this means you'll be plotting the following points:
     *
     * [(2, 6), (2.5, 7.5), (3, 9), (3.5, 10.5), (4, 12), (4.5, 13.5), (5, 15)]
     *
     * ---
     *
     * Another example: now, we're plotting the quadratic equation "a^2 + 4a + 4"
     * from -10 to 10 in 0.01 increments. In this case, "a" is our "x" variable.
     *
     * >>> c := 4
     * 4
     * >>> step := 0.01
     * 0.01
     * >>> plot(a^2 + c*a + a, a, -10, 10, step)
     *
     * ---
     *
     * @throws EvaluationError  if any of the child expressions other than 'var' contains an undefined variable
     * @throws EvaluationError  if 'var' contains a defined variable or is not a variable
     * @throws EvaluationError  if varMin > varMax
     * @throws EvaluationError  if 'step' is zero or negative
     */
    public static AstNode handlePlot(AstNode node, IDictionary<String, AstNode> variables, ImageDrawer drawer) {
        AstNode.assertOperatorValid("plot", 5, node);
        // Children are : (exprToPlot, var, varMin, varMax, step)
        IList<AstNode> children = node.getChildren();
        // exprToPlot is the expression used for the "Y" value
        // var is the "X" value
        // min is the starting "X" value
        // max is the ending "X" value
        // step is the value we increment x by to get to the ending value
        AstNode exprToPlot = children.get(0);
        AstNode var = children.get(1);
        double min = ExpressionOperators.toDoubleHelper(children.get(2), variables);
        double max = ExpressionOperators.toDoubleHelper(children.get(3), variables);
        AstNode step = children.get(4);

        // Lists of X and Y Values
        IList<Double> xValues = new DoubleLinkedList<>();
        IList<Double> yValues = new DoubleLinkedList<>();

        if (!var.isVariable() || variables.containsKey(var.getName())) {
            // if var is not a variable or if var is already defined in the dictionary
            throw new EvaluationError("The variable passed is not a variable, or it is already defined");
        }
        if (min > max) {
            // if min is greater than max
            throw new EvaluationError("Minimum is greater than the Maximum");
        }
        if (ExpressionOperators.toDoubleHelper(step, variables) <= 0) {
            // if step is negative or is 0
            throw new EvaluationError("Step is zero or negative");
        }

        // loops from min to max incrementing min by the step given
        // and adds the X and Y values to their respective lists during each step
        for (double i = min; i <= max; i += ExpressionOperators.toDoubleHelper(step, variables)) {
            xValues.add(i);
            variables.put(var.getName(), new AstNode(i));
            double yValue = ExpressionOperators.toDoubleHelper(exprToPlot, variables);
            yValues.add(yValue);
        }
        // plots the graph using the X and Y values from the respective lists passed
        drawer.drawScatterPlot("Plot", "x", "output", xValues, yValues);
        // Removes the "temporary" variable put in the dictionary to evaluate the expression during the loop
        variables.remove(var.getName());
        // returns an arbitrary number because it causes less errors. the return does not do anything special
        return new AstNode(1);
    }
}

