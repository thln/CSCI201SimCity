package application.gui.appView.controlPanel;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import application.gui.trace.PrintControlPanel;
import application.gui.trace.TracePanel;

public class PrintPanel extends JPanel {

	PrintControlPanel printControlPanel;
	TracePanel tracePanel;
	
	PrintPanel() {
		tracePanel = new TracePanel();
		printControlPanel = new PrintControlPanel(tracePanel);
		
		setLayout(new BorderLayout());
		this.add(printControlPanel, BorderLayout.NORTH);
		this.add(tracePanel, BorderLayout.CENTER);
	}
}