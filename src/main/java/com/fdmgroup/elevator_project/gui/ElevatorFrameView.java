package com.fdmgroup.elevator_project.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

import com.fdmgroup.elevator_project.controller.ElevatorLogic;
import com.fdmgroup.elevator_project.properties.Properties;

public class ElevatorFrameView {
	private Properties properties = new Properties();
	private final int numLift = properties.getNumElevator();
	private final int gHeight = properties.getNumFloor();
	private ElevatorLogic elevatorLogic = new ElevatorLogic();

	JFrame f;

	public ElevatorFrameView(final int maxFloor) {
		
//    	ElevatorLogger.debug("GUI Initialised");
		f = new JFrame();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Setting Frame width and height
		f.setSize(1080, 1050);

		// Setting the title of Frame
		f.setTitle("Big Brain -  Elevator Project");

		// Setting the layout for the Frame
		f.add(new JComponent() {
			{
				final Timer timer = new Timer(50, null);
				timer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						repaint();
					}
				});
				timer.start();
			}

			public void paintComponent(Graphics g) {
				int level = (getHeight() / (maxFloor + 1));
				for (int i = 0; i <= (maxFloor + 1); i++) {
					g.drawString(Integer.toString(i), 0, getHeight() - gHeight - level * i);
					g.drawLine(0, getHeight() - gHeight - level * i, f.getWidth(), getHeight() - gHeight - level * i);
				}

				g.setColor(Color.black);
				g.fillRect(0, getHeight() - gHeight, getWidth(), gHeight);

				int lWidth = 40;
				int lHeight = 50;

				int sizeModifier = numLift / 10;

				if (sizeModifier > 1) {
					lWidth = lWidth / sizeModifier;
					lHeight = lHeight / sizeModifier;
				}

				for (int i = 0; i < numLift; i++) {
					g.setColor(Color.blue);
					g.fillRect(getWidth() / 2 / numLift + (i * 2 * lWidth),
							(getHeight() - lHeight - gHeight) - elevatorLogic.getElevatorList().get(i).getCurrentFloor() * level,
							lWidth, lHeight);
				}

			}
		});

		/*
		 * By default frame is not visible so we are setting the visibility to true to
		 * make it visible.
		 */
		f.setVisible(true);
	}
}
