package control;

import model.*;

public class Controller {
	
	
	/***
	 * Eintrittspunkt in das Programm
	 * @param args die beim Programmstart uebergebenen Parameter
	 */
	public static void main(String[] args) {
		Map map = new Map();
			
		String fp = args[0];
		String fn = args[1];
		map.setIteration(Integer.parseInt(args[2]));
		int preIter = Integer.parseInt(args[3]);
		int calcMode = Integer.parseInt(args[4]);
		String ext = args[5];
			
		IOHandler ioHandler = IOHandler.getInstance();
		ioHandler.read(fp + fn, map);
		
		
		for (int i = 0; i < map.getIteration(); i++) {	
			if (i == 0 && preIter == 1) {
				map.preCalculation();
			}	
			
			if (calcMode == 0) {
				map.calForces();
			} else {
				// Alternative, die nicht fertig ausgearbeitet wurde
				map.calcForces2();
			}
		}
		map.calcRange();
		ioHandler.write(fp, fn, ext, map);
	}

}
