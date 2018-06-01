import javax.swing.JOptionPane;

public class menu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int estado;
		
		estado = Integer.parseInt(JOptionPane.showInputDialog("Menu\n1. PLAY\n2.CLOSE"));
		
		switch (estado) {
		
		case 1:
			JOptionPane.showMessageDialog(null, "INICIO");
			
			break;
		case 2:
			JOptionPane.showMessageDialog(null, "BYE");
		}
		
	}

}
