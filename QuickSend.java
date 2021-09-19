package main;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import utils.BillUtils;
import utils.ExcelUtils;
import utils.GeneralUtils;
import utils.WhatsappUtils;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class QuickSend {

	private JFrame frmWaterBillQuicksend;
	private JTextField GFCheckNamesTF;
	private JTextField FFCheckNamesTF;
	private JTextField SSCheckNamesTF;
	private JTextField SMCheckNamesTF;
	private JTextField SLCheckNamesTF;
	private JTextField GFConsumptionTF;
	private JTextField FFConsumptionTF;
	private JTextField SSConsumptionTF;
	private JTextField SMConsumptionTF;
	private JTextField SLConsumptionTF;

	// Control Fields
	static boolean isGenerateBillSelected = true;

	static boolean isGFChecked = false;
	static boolean isFFChecked = false;
	static boolean isSSChecked = false;
	static boolean isSMChecked = false;
	static boolean isSLChecked = false;

	static String month = "";
	static String pumpingCharge = "";
	static String actualBillAmount = "";

	private JTextField enterDetailsMonthTF;
	private JTextField enterDetailsPumpingChargeTF;
	private JTextField enterDetailsActualAmountTF;

	/**
	 * Launch the application.
	 * 
	 * @throws IOException
	 */

	static ExcelUtils eu;

	public static void main(String[] args) throws IOException, URISyntaxException {

		// Accessing Excel File
		// Default excelPath
		String excelPath =   QuickSend.class.getResource("../").getFile().toString();
		excelPath = excelPath.substring(0,excelPath.lastIndexOf("/"));
		excelPath = excelPath.substring(0,excelPath.lastIndexOf("/"));
		excelPath = excelPath.substring(0,excelPath.lastIndexOf("/"));
		excelPath = excelPath+"/data/WaterData.xlsx";
		System.out.println("The init path is " + excelPath);

//		File file;
//		file = new File(QuickSend.class.getResource("/data/WaterData.xslx").toURI());
		
		
		File directory = new File(QuickSend.class.getResource(".././").toURI());

        File[] filesArray = directory.listFiles();
        System.out.println("Printing files in " + QuickSend.class.getResource(".././").toURI() );

        //sort all files
        Arrays.sort(filesArray);

        //print the sorted values
        for (File file : filesArray) {
            if (file.isFile()) {
                System.out.println("File : " + file.getName());
            } else if (file.isDirectory()) {
                System.out.println("Directory : " + file.getName());
            } else {
                System.out.println("Unknown : " + file.getName());
            }
        }

		// Opening the File Explorer through a dialog bog box and getting the excel
		// file.
//		FileDialog fd = new FileDialog(new JFrame());
//		fd.setFile("*.xlsx");
//		fd.setVisible(true);
//		File file[] = fd.getFiles();
//		if (file.length > 0) {
//			// Setting excel path to value selected through dialog box
//			excelPath = file[0].getAbsolutePath();
//		}
		
		
		System.out.println("The path is " + excelPath);
		eu = new ExcelUtils(excelPath);

		// Get House Occupant Whatsapp Names
		final String[] whatsappNames = eu.getHouseOccupantNames();
		System.out.println(Arrays.toString(whatsappNames));

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuickSend window = new QuickSend(whatsappNames);
					window.frmWaterBillQuicksend.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public QuickSend(String[] whatsappNames) {
		initialize(whatsappNames);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final String[] whatsappNames) {
		frmWaterBillQuicksend = new JFrame();
		frmWaterBillQuicksend.setTitle("Water Bill QuickSend");
		frmWaterBillQuicksend.setBounds(100, 100, 450, 300);
		frmWaterBillQuicksend.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWaterBillQuicksend.getContentPane().setBounds(0, 0, 450, 300);
		frmWaterBillQuicksend.getContentPane()
				.setLayout(new BoxLayout(frmWaterBillQuicksend.getContentPane(), BoxLayout.Y_AXIS));

		final JPanel mainPanel = new JPanel();
		frmWaterBillQuicksend.getContentPane().add(mainPanel);
		mainPanel.setLayout(new CardLayout(0, 0));
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));
		mainPanel.setPreferredSize(new Dimension(400, 200));
		mainPanel.setMinimumSize(new Dimension(400, 200));
		mainPanel.setMaximumSize(new Dimension(400, 200));

		JPanel billTypePanel = new JPanel();
		mainPanel.add(billTypePanel, "billTypeCard");
		billTypePanel.setName("billTypeCard");
		billTypePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		billTypePanel.setLayout(null);

		JLabel billTypeTitleLabel = new JLabel("Select Bill Type");
		billTypeTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		billTypeTitleLabel.setBounds(0, 10, 400, 20);
		billTypeTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		billTypePanel.add(billTypeTitleLabel);

		final JRadioButton ActualBillRdBtn = new JRadioButton("Actual Bill");
		ActualBillRdBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ActualBillRdBtn.setBounds(18, 77, 274, 23);
		ActualBillRdBtn.setSelected(!isGenerateBillSelected);
		billTypePanel.add(ActualBillRdBtn);

		final JRadioButton GenerateBillRdBtn = new JRadioButton("Generate Bill");
		GenerateBillRdBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GenerateBillRdBtn.setBounds(18, 127, 274, 23);
		GenerateBillRdBtn.setSelected(isGenerateBillSelected);
		billTypePanel.add(GenerateBillRdBtn);

	

		final JPanel enterDetailsPanel = new JPanel();
		mainPanel.add(enterDetailsPanel, "enterDetailsCard");
		enterDetailsPanel.setName("enterDetailsCard");
		enterDetailsPanel.setLayout(null);

		JLabel enterDetailsTitleLabel = new JLabel("Enter Bill Details");
		enterDetailsTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		enterDetailsTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		enterDetailsTitleLabel.setBounds(0, 11, 398, 20);
		enterDetailsPanel.add(enterDetailsTitleLabel);

		JLabel enterDetailsMonthLabel = new JLabel("Month");
		enterDetailsMonthLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		enterDetailsMonthLabel.setBounds(29, 61, 125, 26);
		enterDetailsPanel.add(enterDetailsMonthLabel);

		enterDetailsMonthTF = new JTextField();
		enterDetailsMonthTF.setBounds(180, 66, 180, 20);
		enterDetailsMonthTF.setColumns(10);
		enterDetailsMonthTF.setText(month);
		enterDetailsPanel.add(enterDetailsMonthTF);

		JLabel enterDetailsPumpingChargeLabel = new JLabel("Pumping Charge");
		enterDetailsPumpingChargeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		enterDetailsPumpingChargeLabel.setBounds(29, 98, 125, 26);
		enterDetailsPanel.add(enterDetailsPumpingChargeLabel);

		enterDetailsPumpingChargeTF = new JTextField();
		enterDetailsPumpingChargeTF.setColumns(10);
		enterDetailsPumpingChargeTF.setBounds(180, 105, 180, 20);
		enterDetailsPumpingChargeTF.setText(pumpingCharge + "");
		enterDetailsPanel.add(enterDetailsPumpingChargeTF);

		enterDetailsPumpingChargeTF.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				check();
			}

			public void removeUpdate(DocumentEvent e) {
				check();
			}

			public void insertUpdate(DocumentEvent e) {
				check();
			}

			public void check() {
				String val = enterDetailsPumpingChargeTF.getText();
				if (val.length() > 0 && !isNumeric(val)) {
					JOptionPane.showMessageDialog(null, "Error: Please enter a number", "Error Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		final JLabel enterDetailsActualAmountLabel = new JLabel("Actual Bill Amount");
		enterDetailsActualAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		enterDetailsActualAmountLabel.setBounds(29, 135, 125, 26);
		if (!isGenerateBillSelected) {
			enterDetailsPanel.add(enterDetailsActualAmountLabel);
		}
		enterDetailsActualAmountTF = new JTextField();
		enterDetailsActualAmountTF.setColumns(10);
		enterDetailsActualAmountTF.setBounds(180, 136, 180, 20);
		enterDetailsActualAmountTF.setText(actualBillAmount + "");

		enterDetailsActualAmountTF.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				check();
			}

			public void removeUpdate(DocumentEvent e) {
				check();
			}

			public void insertUpdate(DocumentEvent e) {
				check();
			}

			public void check() {
				String val = enterDetailsActualAmountTF.getText();
				if (val.length() > 0 && !isNumeric(val)) {
					JOptionPane.showMessageDialog(null, "Error: Please enter a number", "Error Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		if (!isGenerateBillSelected) {
			enterDetailsPanel.add(enterDetailsActualAmountTF);
		}
		
		
		
		
		ActualBillRdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ActualBillRdBtn.setSelected(true);
				GenerateBillRdBtn.setSelected(false);
				enterDetailsPanel.add(enterDetailsActualAmountLabel);
				enterDetailsPanel.add(enterDetailsActualAmountTF);
				isGenerateBillSelected = false;
			}
		});

		GenerateBillRdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActualBillRdBtn.setSelected(false);
				GenerateBillRdBtn.setSelected(true);
				enterDetailsPanel.remove(enterDetailsActualAmountLabel);
				enterDetailsPanel.remove(enterDetailsActualAmountTF);
				isGenerateBillSelected = true;
			}
		});
		
		
		

		JPanel selectHousesPanel = new JPanel();
		mainPanel.add(selectHousesPanel, "selectHousesCard");
		selectHousesPanel.setName("selectHousesCard");
		selectHousesPanel.setLayout(null);

		JLabel selectHousesTitleLabel = new JLabel("Select Houses");
		selectHousesTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectHousesTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		selectHousesTitleLabel.setBounds(0, 10, 400, 20);
		selectHousesPanel.add(selectHousesTitleLabel);

		final JCheckBox GFSelectHouseCB = new JCheckBox("Ground Floor");
		GFSelectHouseCB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GFSelectHouseCB.setBounds(24, 51, 368, 23);
		GFSelectHouseCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				isGFChecked = GFSelectHouseCB.isSelected();
			}
		});
		selectHousesPanel.add(GFSelectHouseCB);

		final JCheckBox FFSelectHouseCB = new JCheckBox("First Floor");
		FFSelectHouseCB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		FFSelectHouseCB.setBounds(24, 77, 368, 23);
		FFSelectHouseCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				isFFChecked = FFSelectHouseCB.isSelected();

			}
		});
		selectHousesPanel.add(FFSelectHouseCB);

		final JCheckBox SSSelectHouseCB = new JCheckBox("Second Floor Single");
		SSSelectHouseCB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		SSSelectHouseCB.setBounds(24, 103, 368, 23);
		SSSelectHouseCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				isSSChecked = SSSelectHouseCB.isSelected();
			}
		});
		selectHousesPanel.add(SSSelectHouseCB);

		final JCheckBox SMSelectHouseCB = new JCheckBox("Second Floor Mid");
		SMSelectHouseCB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		SMSelectHouseCB.setBounds(24, 129, 368, 23);
		SMSelectHouseCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				isSMChecked = SMSelectHouseCB.isSelected();
			}
		});
		selectHousesPanel.add(SMSelectHouseCB);

		final JCheckBox SLSelectHouseCB = new JCheckBox("Second Floor Last");
		SLSelectHouseCB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		SLSelectHouseCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				isSLChecked = SLSelectHouseCB.isSelected();
			}
		});
		SLSelectHouseCB.setBounds(24, 155, 368, 23);
		selectHousesPanel.add(SLSelectHouseCB);

		JPanel checkNamesPanel = new JPanel();
		mainPanel.add(checkNamesPanel, "checkNamesCard");
		checkNamesPanel.setName("checkNamesCard");
		checkNamesPanel.setLayout(null);

		JLabel checkNamesTitleLabel = new JLabel("Check Names");
		checkNamesTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		checkNamesTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		checkNamesTitleLabel.setBounds(0, 11, 398, 20);
		checkNamesPanel.add(checkNamesTitleLabel);

		JLabel GFCheckNamesLabel = new JLabel("Ground Floor");
		GFCheckNamesLabel.setBounds(29, 42, 86, 20);
		checkNamesPanel.add(GFCheckNamesLabel);

		JLabel FFCheckNamesLabel = new JLabel("First Floor");
		FFCheckNamesLabel.setBounds(29, 73, 86, 20);
		checkNamesPanel.add(FFCheckNamesLabel);

		JLabel SSCheckNamesLabel = new JLabel("Second Single");
		SSCheckNamesLabel.setBounds(29, 104, 86, 20);
		checkNamesPanel.add(SSCheckNamesLabel);

		JLabel SMCheckNamesLabel = new JLabel("Second Mid");
		SMCheckNamesLabel.setBounds(29, 135, 86, 20);
		checkNamesPanel.add(SMCheckNamesLabel);

		JLabel SLCheckNamesLabel = new JLabel("Second Last");
		SLCheckNamesLabel.setBounds(29, 167, 86, 20);
		checkNamesPanel.add(SLCheckNamesLabel);

		GFCheckNamesTF = new JTextField();
		GFCheckNamesTF.setBounds(115, 42, 250, 20);
		GFCheckNamesTF.setColumns(10);
		GFCheckNamesTF.setText(whatsappNames[0]);
		checkNamesPanel.add(GFCheckNamesTF);

		FFCheckNamesTF = new JTextField();
		FFCheckNamesTF.setColumns(10);
		FFCheckNamesTF.setBounds(115, 73, 250, 20);
		FFCheckNamesTF.setText(whatsappNames[1]);
		checkNamesPanel.add(FFCheckNamesTF);

		SSCheckNamesTF = new JTextField();
		SSCheckNamesTF.setColumns(10);
		SSCheckNamesTF.setBounds(115, 104, 250, 20);
		SSCheckNamesTF.setText(whatsappNames[2]);
		checkNamesPanel.add(SSCheckNamesTF);

		SMCheckNamesTF = new JTextField();
		SMCheckNamesTF.setColumns(10);
		SMCheckNamesTF.setBounds(115, 135, 250, 20);
		SMCheckNamesTF.setText(whatsappNames[3]);
		checkNamesPanel.add(SMCheckNamesTF);

		SLCheckNamesTF = new JTextField();
		SLCheckNamesTF.setColumns(10);
		SLCheckNamesTF.setBounds(115, 167, 250, 20);
		SLCheckNamesTF.setText(whatsappNames[4]);
		checkNamesPanel.add(SLCheckNamesTF);

		JPanel consumptionPanel = new JPanel();
		mainPanel.add(consumptionPanel, "consumptionCard");
		consumptionPanel.setName("consumptionCard");
		consumptionPanel.setLayout(null);

		JLabel consumptionTitleLabel = new JLabel("Enter Consumption");
		consumptionTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		consumptionTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		consumptionTitleLabel.setBounds(0, 11, 398, 20);
		consumptionPanel.add(consumptionTitleLabel);

		JLabel GFConsumptionLabel = new JLabel("Ground Floor");
		GFConsumptionLabel.setBounds(29, 42, 87, 20);
		consumptionPanel.add(GFConsumptionLabel);

		GFConsumptionTF = new JTextField();
		GFConsumptionTF.setBounds(115, 42, 273, 20);
		consumptionPanel.add(GFConsumptionTF);
		GFConsumptionTF.setColumns(10);

		JLabel FFConsumptionLabel = new JLabel("First Floor");
		FFConsumptionLabel.setBounds(29, 73, 87, 20);
		consumptionPanel.add(FFConsumptionLabel);

		FFConsumptionTF = new JTextField();
		FFConsumptionTF.setColumns(10);
		FFConsumptionTF.setBounds(115, 73, 273, 20);
		consumptionPanel.add(FFConsumptionTF);

		JLabel SSConsumptionLabel = new JLabel("Second Single");
		SSConsumptionLabel.setBounds(29, 104, 87, 20);
		consumptionPanel.add(SSConsumptionLabel);

		SSConsumptionTF = new JTextField();
		SSConsumptionTF.setColumns(10);
		SSConsumptionTF.setBounds(115, 104, 273, 20);
		consumptionPanel.add(SSConsumptionTF);

		JLabel SMConsumptionLabel = new JLabel("Second Mid");
		SMConsumptionLabel.setBounds(29, 135, 87, 20);
		consumptionPanel.add(SMConsumptionLabel);

		SMConsumptionTF = new JTextField();
		SMConsumptionTF.setColumns(10);
		SMConsumptionTF.setBounds(115, 135, 273, 20);
		consumptionPanel.add(SMConsumptionTF);

		JLabel SLConsumptionLabel = new JLabel("Second Last");
		SLConsumptionLabel.setBounds(29, 166, 87, 20);
		consumptionPanel.add(SLConsumptionLabel);

		SLConsumptionTF = new JTextField();
		SLConsumptionTF.setColumns(10);
		SLConsumptionTF.setBounds(115, 166, 273, 20);
		consumptionPanel.add(SLConsumptionTF);

		JPanel nextButtonHolderPanel = new JPanel();
		nextButtonHolderPanel.setPreferredSize(new Dimension(400, 50));
		nextButtonHolderPanel.setMinimumSize(new Dimension(400, 50));
		nextButtonHolderPanel.setMaximumSize(new Dimension(400, 50));
		nextButtonHolderPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1, false));
		frmWaterBillQuicksend.getContentPane().add(nextButtonHolderPanel);
		nextButtonHolderPanel.setLayout(null);

		final JButton nextButton = new JButton("Next");
		nextButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nextButton.setBounds(296, 16, 94, 23);
		nextButtonHolderPanel.add(nextButton);
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// Getting the current card's name

				String currentCardName = "";
				for (Component comp : mainPanel.getComponents()) {
					if (comp.isVisible()) {
						currentCardName = comp.getName();
						System.out.println("Current Card = " + currentCardName);
					}
				}
				// Traverse to next card until consumptions card
				if (!currentCardName.equals("consumptionCard")) {
					CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
					if(currentCardName.equals("checkNamesCard")) {
						nextButton.setText("Send");
					}
					
					cardLayout.next(mainPanel);
				} else {
					// Getting Previous Month Consumption Data
					int[] prevConsumption = eu.getQuickSendPrevConsumption();
					System.out.println("Prev Consumption : " + Arrays.toString(prevConsumption));

					// Getting Current Month Consumption
					int[] currConsumption = getCurrentMonthConsumption();
					System.out.println("Curr Consumption : " + Arrays.toString(currConsumption));

					// Getting Corrected Current Month Consumption by substituting prev month
					// consumption
					// at places where fresh data is not entered ( replaced by 0 by the function
					// getCurrentMonthConsumption()
					currConsumption = getCorrectedCurrentMonthConsumption(prevConsumption, currConsumption);
					System.out.println("Corrected Consumption : " + Arrays.toString(currConsumption));

					// Getting the water usage of that house for the current month
					int[] usage = getUsage(prevConsumption, currConsumption);
					System.out.println("Usage : " + Arrays.toString(usage));

					// Getting the meter factor for the current month
					double[] meterFactor = eu.getQuickSendMeterFactor();
					System.out.println("Meter Factor : " + Arrays.toString(meterFactor));

					// Getting Corrected Consumption for the month
					double[] correctedConsumption = getCorrectedConsumption(meterFactor, usage);
					System.out.println("Corrected Consumption " + Arrays.toString(correctedConsumption));

					// Total Consumption for the month
					double totalConsumption = GeneralUtils.sumDoubleArray(correctedConsumption);
					System.out.println("Total Consumption : " + totalConsumption);

					// Pumping Charge
					// Next two lines for test purposes only
					String pumpingChargeVal = enterDetailsPumpingChargeTF.getText();
					pumpingChargeVal = pumpingChargeVal.trim();
					pumpingChargeVal = (pumpingChargeVal.equals("") ? 100 + "" : pumpingChargeVal);
					int pumpingCharge = Integer.parseInt(pumpingChargeVal);
					System.out.println("Pumping Charge : " + pumpingCharge);

					// Calculating total water bill for the month
					double totalWaterBill = 0;
					if(isGenerateBillSelected) {
						totalWaterBill = BillUtils.calculateBill(totalConsumption);
					} else {
						String totalWaterBillVal = enterDetailsActualAmountTF.getText();
						totalWaterBillVal = totalWaterBillVal.equals("")?""+0:totalWaterBillVal;
						totalWaterBill = Integer.parseInt(totalWaterBillVal);
					}

					// Calculating total bill (water + pumping charge)
					double totalBill = totalWaterBill + pumpingCharge;
					System.out.println("Total Bill is " + totalBill);

					// Calculating bill to be paid by each house occupant
					double houseBill[] = getHouseBill(totalBill, totalConsumption, correctedConsumption);
					System.out.println("Bill by house is " + Arrays.toString(houseBill));
					
					
					//Send Bill if house is selected
					try {
						sendBill(houseBill);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}

			}

			private void sendBill(double[] houseBill) throws InterruptedException {
				WhatsappUtils.Authenticate();
				String month = enterDetailsMonthTF.getText();
				String message = "";
				// TODO Auto-generated method stub
				if(GFSelectHouseCB.isSelected()) {
					message = String.format("Your monthly bill for %s is %2d", month, (int)houseBill[0]);
					WhatsappUtils.sendMessage(whatsappNames[0], message);
				} if(FFSelectHouseCB.isSelected()) {
					message = String.format("Your monthly bill for %s is %2d", month, (int)houseBill[1]);
					WhatsappUtils.sendMessage(whatsappNames[1], message);
				} if(SSSelectHouseCB.isSelected()) {
					message = String.format("Your monthly bill for %s is %2d", month, (int)houseBill[2]);
					WhatsappUtils.sendMessage(whatsappNames[2], message);
				} if(SMSelectHouseCB.isSelected()) {
					message = String.format("Your monthly bill for %s is %2d", month, (int)houseBill[3]);
					WhatsappUtils.sendMessage(whatsappNames[3], message);
				} if(SLSelectHouseCB.isSelected()) {
					message = String.format("Your monthly bill for %s is %2d", month, (int)houseBill[4]);
					WhatsappUtils.sendMessage(whatsappNames[4], message);
				}
			}

			private double[] getHouseBill(double totalBill, double totalConsumption, double[] correctedConsumption) {
				// TODO Auto-generated method stub
				double houseBill[] = new double[5];
				for (int i = 0; i < 5; i++) {
					houseBill[i] = Math.round((correctedConsumption[i] / totalConsumption) * totalBill);
				}
				return houseBill;
			}

			private double[] getCorrectedConsumption(double[] meterFactor, int[] usage) {
				// TODO Auto-generated method stub

				double correctedConsumption[] = new double[5];
				for (int i = 0; i < 5; i++) {
					correctedConsumption[i] = usage[i] * meterFactor[i];
				}

				return correctedConsumption;
			}

			private int[] getUsage(int[] prevConsumption, int[] currConsumption) {
				// TODO Auto-generated method stub
				int[] usage = new int[5];
				for (int i = 0; i < currConsumption.length; i++) {
					usage[i] = currConsumption[i] - prevConsumption[i];
				}
				return usage;
			}

			private int[] getCorrectedCurrentMonthConsumption(int[] prevConsumption, int[] currConsumption) {
				// TODO Auto-generated method stub
				for (int i = 0; i < currConsumption.length; i++) {
					if (currConsumption[i] == 0) {
						currConsumption[i] = prevConsumption[i];
					}
				}
				return currConsumption;
			}

			private int[] getCurrentMonthConsumption() {
				// TODO Auto-generated method stub
				int[] consumption = new int[5];
				String GFConsumptionVal = GFConsumptionTF.getText();
				if (GFConsumptionVal.equals("")) {
					consumption[0] = 0;
				} else {
					consumption[0] = Integer.parseInt(GFConsumptionVal);
				}

				String FFConsumptionVal = FFConsumptionTF.getText();
				if (FFConsumptionVal.equals("")) {
					consumption[1] = 0;
				} else {
					consumption[1] = Integer.parseInt(FFConsumptionVal);
				}

				String SSConsumptionVal = SSConsumptionTF.getText();
				if (SSConsumptionVal.equals("")) {
					consumption[2] = 0;
				} else {
					consumption[2] = Integer.parseInt(SSConsumptionVal);
				}

				String SMConsumptionVal = SMConsumptionTF.getText();
				if (SMConsumptionVal.equals("")) {
					consumption[3] = 0;
				} else {
					consumption[3] = Integer.parseInt(SMConsumptionVal);
				}

				String SLConsumptionVal = SLConsumptionTF.getText();
				if (SLConsumptionVal.equals("")) {
					consumption[4] = 0;
				} else {
					consumption[4] = Integer.parseInt(SLConsumptionVal);
				}
				return consumption;
			}
		});
	}

	public static boolean isNumeric(String val) {
		try {
			Integer.parseInt(val);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
