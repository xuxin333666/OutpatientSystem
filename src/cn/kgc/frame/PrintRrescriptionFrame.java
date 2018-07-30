package cn.kgc.frame;

import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.kgc.model.Case;
import cn.kgc.model.Medicine;
import cn.kgc.model.Patient;
import cn.kgc.model.Prescription;
import cn.kgc.model.PrescriptionMedicine;
import cn.kgc.service.impl.MedicineServiceImpl;
import cn.kgc.service.intf.MedicineService;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.PatientUtils;

public class PrintRrescriptionFrame {
	private JTextField TextField1;
	private JTextField TextField2;
	private JTextField TextField3;
	private JTextField TextField4;
	private JTextField TextField5;
	private JTextField TextField6;
	private JTextField TextField7;
	private JTextField TextField8;
	private JTextArea TextField9;
	private JTextField TextField10;
	private JTextField TextField11;
	private JTextField TextField12;
	private JTextField TextField13;
	

	public PrintRrescriptionFrame(Prescription prescription, List<PrescriptionMedicine> prescriptionMedicines) {
		Case $case = prescription.get$case();
		Patient patient = prescription.getPatient();
		
		
		
		JFrame frame = new JFrame("西医处方打印");
		frame.setSize(500, 700);
		frame.setLayout(null);
		
		//添加标签-----------------------------------------------
		JLabel jLabel1 = new JLabel("医疗证号:");
		JLabel jLabel2 = new JLabel("处方号:");
		JLabel jLabel3 = new JLabel("姓名:");
		JLabel jLabel4 = new JLabel("性别:");
		JLabel jLabel5 = new JLabel("年龄:");
		JLabel jLabel6 = new JLabel("主述:");
		JLabel jLabel7 = new JLabel("诊断:");
		JLabel jLabel8 = new JLabel("地址:");
		JLabel jLabel9 = new JLabel("医嘱:");
		JLabel jLabel10 = new JLabel("医师:");
		JLabel jLabel11 = new JLabel("药费:");
		
		jLabel1.setBounds(10, 10, 60, 25);
		jLabel2.setBounds(300,35, 90,20);//处方号
		jLabel3.setBounds(10,  80, 60, 25);
		jLabel4.setBounds(115, 80, 60, 25);
		jLabel5.setBounds(220, 80, 60, 25);
		jLabel6.setBounds(325, 80, 60, 25);
		jLabel7.setBounds(10, 105, 60, 25);
		jLabel8.setBounds(10,130, 60, 25);
		
		jLabel9.setBounds(10, 510, 60, 25);
		jLabel10.setBounds(10,535, 60, 25);
		jLabel11.setBounds(320,580, 60, 25);
		
		frame.add(jLabel1);
		frame.add(jLabel2);
		frame.add(jLabel3);
		frame.add(jLabel4);
		frame.add(jLabel5);
		frame.add(jLabel6);
		frame.add(jLabel7);
		frame.add(jLabel8);
		frame.add(jLabel9);
		frame.add(jLabel10);
		frame.add(jLabel11);
		
		TextField1 = new JTextField();
		TextField1.setText(patient.getId());
		TextField2 = new JTextField();
		TextField2.setText(prescription.getId()); 
		TextField3 = new JTextField();
		TextField3.setText(patient.getName());
		TextField4 = new JTextField();
		TextField4.setText(PatientUtils.status2Str(patient.getSex(), PatientUtils.sexRule));
		TextField5 = new JTextField();
		TextField5.setText(patient.getAge().toString());
		TextField6 = new JTextField();
		TextField6.setText($case.getMainSymptom());
		TextField7 = new JTextField();
		TextField7.setText($case.getExamination());
		TextField8 = new JTextField();
		TextField8.setText(patient.getAddress());
		TextField9 = new JTextArea();
		StringBuilder medicineList = new StringBuilder("药品名称\t药品单价\t药品分类\t药品用法\n");
		MedicineService medicineService = new MedicineServiceImpl();
		for (PrescriptionMedicine prescriptionMedicine : prescriptionMedicines) {
			try {
				Medicine medicine = medicineService.getInfoById(prescriptionMedicine.getMedicineId());
				medicineList.append(medicine.getName() + "\t");
				medicineList.append(medicine.getPrice() + "元\t");
				medicineList.append(medicine.getMedicineType().getName() + "\t");
				medicineList.append(prescriptionMedicine.getUasge() + "\n");
			} catch (Exception e) {
				FrameUtils.DialogErorr(e.getMessage());
				e.printStackTrace();
			}
		}
		TextField9.setText(medicineList.toString());
		TextField10 = new JTextField();
		TextField10.setText(prescription.getAdvice());
		TextField11 = new JTextField();
		TextField12 = new JTextField();
		TextField13 = new JTextField();
		if(prescription.getPrice() == null) {
			TextField13.setText("");
		} else {
			TextField13.setText(prescription.getPrice().toString());		
		}
		
		
		JTextField Field1 = new JTextField("易软门诊处方笺");
		Field1.setBounds(120,40, 180,20);//某医院
		Field1.setBorder(null);
		Field1.setFont(new Font("楷体",Font.BOLD,20));
		Field1.setOpaque(false);
		Field1.setEditable(false);
		frame.add(Field1);
		JTextField Field2 = new JTextField("三甲医院");
		Field2.setBounds(350,10, 90,20);
		Field2.setFont(new Font("楷体",Font.BOLD,16));
		Field2.setBorder(null);
		Field2.setOpaque(false);
		Field2.setEditable(false);
		frame.add(Field2);
		JTextField Field3 = new JTextField("R");
		Field3.setBounds(10,155, 40,40);//某医院
		Field3.setBorder(null);
		Field3.setOpaque(false);
		Field3.setFont(new Font("楷体",Font.BOLD,40));
		Field3.setEditable(false);
		frame.add(Field3);
		JTextField Field4 = new JTextField("审核  调配  核对  发药");
		Field4.setBounds(30,560, 150,20);//某医院
		Field4.setBorder(null);
		Field4.setOpaque(false);
		Field4.setEditable(false);
		frame.add(Field4);
		
		TextField1.setBounds(70, jLabel1.getY(), 80, 20);
		TextField1.setBorder(null);
		TextField1.setOpaque(false);
		TextField1.setEditable(false);
		TextField2.setBounds(350,35, 90,20);
		TextField2.setBorder(null);
		TextField2.setOpaque(false);
		TextField2.setEditable(false);
		TextField3.setBounds(45, jLabel3.getY(), 60, 20);
		TextField3.setBorder(null);
		TextField3.setOpaque(false);
		TextField3.setEditable(false);
		TextField4.setBounds(150,jLabel4.getY(), 60, 20);
		TextField4.setBorder(null);
		TextField4.setOpaque(false);
		TextField4.setEditable(false);
		TextField5.setBounds(255, jLabel5.getY(), 60, 20);
		TextField5.setBorder(null);
		TextField5.setOpaque(false);
		TextField5.setEditable(false);
		TextField6.setBounds(362, jLabel6.getY(), 60, 20);
		TextField6.setBorder(null);
		TextField6.setOpaque(false);
		TextField6.setEditable(false);
		TextField7.setBounds(50, jLabel7.getY(), 250, 20);
		TextField7.setBorder(null);
		TextField7.setOpaque(false);
		TextField7.setEditable(false);
		TextField8.setBounds(50,jLabel8.getY(), 250, 20);
		TextField8.setBorder(null);
		TextField8.setOpaque(false);
		TextField8.setEditable(false);
		TextField9.setBounds(10,200, 400, 300);//药的信息
		TextField9.setOpaque(false);
		TextField9.setEditable(false);
		TextField10.setBounds(50, 510, 400, 20);//医嘱
		TextField10.setBorder(null);
		TextField10.setOpaque(false);
		TextField10.setEditable(false);
		TextField11.setBounds(50, 535, 100, 20);//医师
		TextField11.setBorder(null);
		TextField11.setOpaque(false);
		TextField11.setEditable(false);
		TextField12.setBounds(250, 535, 150, 20);//日期
		TextField12.setBorder(null);
		TextField12.setOpaque(false);
		TextField12.setEditable(false);
		TextField13.setBounds(355, 580, 80, 20);//费用
		TextField13.setBorder(null);
		TextField13.setOpaque(false);
		TextField13.setEditable(false);
		
		
		frame.add(TextField1);
		frame.add(TextField2);
		frame.add(TextField3);
		frame.add(TextField4);
		frame.add(TextField5);
		frame.add(TextField6);
		frame.add(TextField7);
		frame.add(TextField8);
		frame.add(TextField9);
		frame.add(TextField10);
		frame.add(TextField11);
		frame.add(TextField12);
		frame.add(TextField13);
		
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}


	public JTextField getTextField1() {
		return TextField1;
	}

	public JTextField getTextField2() {
		return TextField2;
	}

	public JTextField getTextField3() {
		return TextField3;
	}

	public JTextField getTextField4() {
		return TextField4;
	}

	public JTextField getTextField5() {
		return TextField5;
	}

	public JTextField getTextField6() {
		return TextField6;
	}

	public JTextField getTextField7() {
		return TextField7;
	}

	public JTextField getTextField8() {
		return TextField8;
	}

	public JTextArea getTextField9() {
		return TextField9;
	}

	public JTextField getTextField10() {
		return TextField10;
	}

	public JTextField getTextField11() {
		return TextField11;
	}

	public JTextField getTextField12() {
		return TextField12;
	}

	public JTextField getTextField13() {
		return TextField13;
	}
	
}
