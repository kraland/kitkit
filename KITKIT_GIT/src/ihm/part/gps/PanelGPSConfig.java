package ihm.part.gps;

import ihm.ConfigIcon;
import ihm.IHMTools;
import ihm.I_Viewable;
import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.gps.I_ModelGPSListener;
import model.gps.Polygon;

import controller.Controller;
import controller.I_ControllerDialog;

@SuppressWarnings("serial")
public class PanelGPSConfig extends JPanel  implements	ActionListener,
														ChangeListener,
														I_Viewable,
														I_ControllerDialog,
														I_ModelGPSListener
{

	/**
	 * Tableau de type de map
	 */
	private static String[] tab_s_typeMap = {"Ete","Halloween","Hiver","Printemps","Médiéval"};
	
	/**
	 * Table contenant toutes les vues
	 */
	private List<I_Viewable> listI_Viewable;
	
	/**
	 * Controlleur
	 */
	private Controller ctrl;
	
	/**
	 * Label affichant le nombre de PdV que coute ce trajet	
	 */
	private JLabel jLabelResult;
	
	/**
	 * Bouton permettant de calculer d'un trajet	
	 */
	private JButton jButtonResult;
	
	/**
	 * 	Bouton permettant de choisir si on affiche une map 2D ou 3D
	 */
	private JButton jButtonScaleMap;

	/**
	 * Spinner permettant de choisir la vitesse de deplacement dans l'air
	 */
	private JSpinner jSpinnerSpeedAir;
	
	/**
	 * Label de la vitesse de deplacement dans l'air
	 */
	private JLabel jLabelSpeedAir;

	/**
	 * Spinner permettant de choisir la vitesse de deplacement sur terre
	 */
	private JSpinner jSpinnerSpeedEarth;
	
	/**
	 * Label de la vitesse de deplacement sur terre
	 */
	private JLabel jLabelSpeedEarth;

	/**
	 * Spinner permettant de choisir la vitesse de deplacement sur l'eau
	 */
	private JSpinner jSpinnerSpeedWater;
	
	/**
	 * Label de la vitesse de deplacement sur l'eau
	 */
	private JLabel jLabelSpeedWater;
	
	/**
	 * Bouton indiquant si on authorise ou non les voyage en train
	 */
	private JButton jButtonByTrain;

	/**
	 * Bouton indiquant si on authorise ou non les voyage en bateau
	 */
	private JButton jButtonByBoat;
	
	/**
	 * Bouton indiquant si on authorise ou non les voyage en avion
	 */
	private JButton jButtonByPlane;
	
	/**
	 * Bouton indiquant la position de la souris
	 */
	private JButton jButtonMousePosition;
	
	/**
	 * Bouton indiquant la position de depart
	 */
	private JButton jButtonBeginPosition;
	
	/**
	 * Bouton indiquant la position d'arrivee
	 */
	private JButton jButtonStopPosition;
	
	/**
	 * Label indiquant dans quelle province se trouve la souris
	 */
	private JLabel jLabelPosition1Mouse;
	
	/**
	 * Label indiquant sur quelle case de la province se trouve la souris
	 */
	private JLabel jLabelPosition2Mouse;
	
	/**
	 * Label indiquant dans quelle province se trouve le point de depart
	 */
	private JLabel jLabelPosition1Begin;
	
	/**
	 * Label indiquant sur quelle case de la province se trouve le point de depart
	 */
	private JLabel jLabelPosition2Begin;
	
	/**
	 * Label indiquant dans quelle province se trouve le point d'arrivee
	 */
	private JLabel jLabelPosition1Stop;
	
	/**
	 * Label indiquant sur quelle case de la province se trouve le point d'arrivee
	 */
	private JLabel jLabelPosition2Stop;
	
	/**
	 * JComboBox contenant les differents types de map
	 */
	@SuppressWarnings("rawtypes")
	private JComboBox jComboBoxTypeMap;
	
	/**
	 * JLabel contenant "Type map :"
	 */
	private JLabel jLabelTypeMap;
	
	/**
	 * JPanel permettant de choisir l'echelle de la map
	 */
	private PanelGPSConfigScaleMap jPanelGPSConfigScaleMap;
			
	// Constructeur
	public PanelGPSConfig()
	{
		// On change le layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	
		// On cree des boites
		Box bV, bH, bVtmp; 
		
		// On initialise les objets
    	initObject();
    	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////// On cree la partie Affichage //////////////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	
    	JPanel panelDisplay = new JPanel();
    	panelDisplay.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(IHMTools.colorBorder),"Affichage :"),BorderFactory.createEmptyBorder(1,1,1,1)));
    	panelDisplay.setLayout(new BorderLayout());
    	
    	bV = Box.createVerticalBox();
    	
    	// On ajoute la partie permettant de choisir le type de map (2D ou 3D)
    	bH = Box.createHorizontalBox();
    	bH.add(jButtonScaleMap);
    	bH.add(Box.createHorizontalGlue());
    	bV.add(bH);
    	
    	// On ajoute le panel permettant de choisir l'echelle de la map (2D ou 3D)
    	jPanelGPSConfigScaleMap.setMaximumSize(new Dimension(3000,32));
       	bV.add(jPanelGPSConfigScaleMap);
    	
    	bH = Box.createHorizontalBox();
    	bH.add(jLabelTypeMap);
    	bH.add(Box.createHorizontalStrut(5));
    	bH.add(jComboBoxTypeMap);
    	bH.add(Box.createHorizontalStrut(15));
    	bV.add(Box.createVerticalStrut(5));
    	bV.add(bH);   	      	

       	
    	IHMTools.getInstance().addHorizontalSeparator(bV,5,5);
    	
    	// On ajoute la partie permettant de visualiser sur quelle case se trouve la souris
    	bVtmp = Box.createVerticalBox();
    	bH = Box.createHorizontalBox();
    	bH.add(jButtonMousePosition);
    	bH.add(Box.createHorizontalStrut(5));
    	bH.add(jLabelPosition1Mouse);
    	bH.add(Box.createHorizontalGlue());
    	bVtmp.add(bH);
    	
    	bH = Box.createHorizontalBox();
    	bH.add(jLabelPosition2Mouse);
    	bH.add(Box.createHorizontalGlue());
    	bVtmp.add(bH);
    	bV.add(bVtmp);
    	
    	panelDisplay.add(bV, BorderLayout.CENTER);
    	panelDisplay.setMaximumSize(new Dimension(3000,90));
    	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////// On cree la partie Criteres ///////////////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////    	
    	
    	JPanel panelCriteria = new JPanel();
    	panelCriteria.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(IHMTools.colorBorder),"Critères :"),BorderFactory.createEmptyBorder(1,1,1,1)));
    	panelCriteria.setLayout(new BorderLayout());
    	
    	bV = Box.createVerticalBox();
    	
    	// On ajoute la partie permettant d'afficher le cout en PdV d'un trajet
    	bH = Box.createHorizontalBox();
    	bH.add(jButtonResult);
    	bH.add(Box.createHorizontalStrut(5));
    	bH.add(jLabelResult);
    	bH.add(Box.createHorizontalGlue());
    	bV.add(bH);
    	
    	IHMTools.getInstance().addHorizontalSeparator(bV,5,5);
    	
    	// On ajoute la partie permettant d'afficher la position de depart
    	bVtmp = Box.createVerticalBox();
    	bH = Box.createHorizontalBox();
    	bH.add(jButtonBeginPosition);
       	bH.add(Box.createHorizontalStrut(5));
    	bH.add(jLabelPosition1Begin);
    	bH.add(Box.createHorizontalGlue());
    	bVtmp.add(bH);
    	
    	bH = Box.createHorizontalBox();
    	bH.add(jLabelPosition2Begin);
    	bH.add(Box.createHorizontalGlue());
    	bVtmp.add(bH);
    	bV.add(bVtmp);
    	
    	bV.add(Box.createVerticalStrut(5));
    	
    	// On ajoute la partie permettant d'afficher la position d'arrivee
    	bVtmp = Box.createVerticalBox();
    	bH = Box.createHorizontalBox();
    	bH.add(jButtonStopPosition);
       	bH.add(Box.createHorizontalStrut(5));
    	bH.add(jLabelPosition1Stop);
    	bH.add(Box.createHorizontalGlue());
    	bVtmp.add(bH);
    	
    	bH = Box.createHorizontalBox();
    	bH.add(jLabelPosition2Stop);
    	bH.add(Box.createHorizontalGlue());
    	bVtmp.add(bH);
    	bV.add(bVtmp);
    	
    	IHMTools.getInstance().addHorizontalSeparator(bV,5,5);
    	
		// On ajoute la partie affichant le choix du coefficient de chaque deplacement		
    	JPanel panelSpeed = new JPanel();
    	panelSpeed.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(IHMTools.colorSubBorder),"Vitesse :"),BorderFactory.createEmptyBorder(1,1,1,1)));
    	panelSpeed.setLayout(new GridLayout(3,2)); 	
    	panelSpeed.add(jLabelSpeedAir);
    	panelSpeed.add(jSpinnerSpeedAir);
    	panelSpeed.add(jLabelSpeedWater);
    	panelSpeed.add(jSpinnerSpeedWater);
    	panelSpeed.add(jLabelSpeedEarth);
    	panelSpeed.add(jSpinnerSpeedEarth);
    	panelSpeed.setMaximumSize(new Dimension(3000,144));
    	bV.add(panelSpeed);
    	
    	IHMTools.getInstance().addHorizontalSeparator(bV,5,5);
    	
		// On ajoute la partie affichant le choix du type de transport en commun
    	JPanel panelPublicTransport = new JPanel();
    	panelPublicTransport.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(IHMTools.colorSubBorder),"Transport en commun :"),BorderFactory.createEmptyBorder(1,1,1,1)));
    	panelPublicTransport.setLayout(new BorderLayout());
    	
    	bH = Box.createHorizontalBox();
    	bH.add(Box.createHorizontalGlue());
    	bH.add(jButtonByTrain);
    	bH.add(Box.createHorizontalGlue());
    	bH.add(jButtonByBoat);
    	bH.add(Box.createHorizontalGlue());
    	bH.add(jButtonByPlane);
    	bH.add(Box.createHorizontalGlue());
    	panelPublicTransport.add(bH, BorderLayout.CENTER);    	
    	panelPublicTransport.setMaximumSize(new Dimension(3000,40));
    	bV.add(panelPublicTransport);
    	    	
    	panelCriteria.add(bV,BorderLayout.CENTER);
    	panelCriteria.setMaximumSize(new Dimension(3000,264));
    	
    	
    	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////// On ajoute le panel Affichage et Criteres ////////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    	
    	bV = Box.createVerticalBox();
    	bV.add(panelDisplay);
    	bV.add(panelCriteria);   	
    	bV.add(Box.createVerticalGlue());
    	
    	add(bV,BorderLayout.CENTER);
    }
	
	/**
	 * Initialise les objets
	 */
	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
	public void initObject()
	{
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////// On cree le label affichant le nombre de PdV que coute un trajet calcule /////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		jLabelResult = new JLabel();
		jLabelResult.setText("Calculer le trajet...");

		
		jButtonResult = new JButton();
		jButtonResult.setOpaque(false);
		jButtonResult.setIcon(ConfigIcon.getInstance().HEART);
		jButtonResult.setContentAreaFilled(false);
		jButtonResult.setFocusPainted(false);
		jButtonResult.addActionListener(this);
		jButtonResult.setMargin(new Insets(0, 0, 0, 0));
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////// Choix du type de MAP ///////////////////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		jButtonScaleMap = IHMTools.getInstance().getNewButtonWithIcon_ActionListener_TextOnRight(ConfigIcon.getInstance().GPS_2D,this,"2D");
		
		// On initialise la Combo
		jComboBoxTypeMap = new JComboBox(tab_s_typeMap);
		jComboBoxTypeMap.setSelectedIndex(0);
		jComboBoxTypeMap.setVisible(false);
		
		jLabelTypeMap = new JLabel("Type map :");
		jLabelTypeMap.setVisible(false);
		
		// On ajoute un listener a la combo
		jComboBoxTypeMap.addActionListener(this);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////// Choix du coefficient de chaque deplacement ////////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		jLabelSpeedAir = new JLabel();
		jLabelSpeedAir.setText("Air : ");
		jLabelSpeedAir.setOpaque(false);
		//jLabelSpeedAir.setIcon(ConfigIcon.getInstance().COEFF_AIR);
		//jLabelSpeedAir.setVerticalTextPosition(SwingConstants.CENTER);
		//jLabelSpeedAir.setHorizontalTextPosition(SwingConstants.RIGHT);
    	
		jLabelSpeedEarth = new JLabel();
		jLabelSpeedEarth.setText("Terre : ");
		jLabelSpeedEarth.setOpaque(false);
		//jLabelSpeedEarth.setIcon(ConfigIcon.getInstance().COEFF_EARTH);
		//jLabelSpeedEarth.setVerticalTextPosition(SwingConstants.CENTER);
		//jLabelSpeedEarth.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		jLabelSpeedWater = new JLabel();
		jLabelSpeedWater.setText("Mer : ");
		jLabelSpeedWater.setOpaque(false);
		//jLabelSpeedWater.setIcon(ConfigIcon.getInstance().COEFF_WATER);
		//jLabelSpeedWater.setVerticalTextPosition(SwingConstants.CENTER);
		//jLabelSpeedWater.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		jSpinnerSpeedAir = new JSpinner(new SpinnerNumberModel(0,0,5,0.1));
		jSpinnerSpeedEarth = new JSpinner(new SpinnerNumberModel(1,1,5,0.1));
		jSpinnerSpeedWater = new JSpinner(new SpinnerNumberModel(0,0,5,0.1));
		
		
		jSpinnerSpeedAir.setEnabled(false);
		jSpinnerSpeedEarth.setEnabled(false);
		jSpinnerSpeedWater.setEnabled(false);
		
		jSpinnerSpeedAir.setPreferredSize(new Dimension(40, 20));
		jSpinnerSpeedEarth.setPreferredSize(new Dimension(40, 20));
		jSpinnerSpeedWater.setPreferredSize(new Dimension(40, 20));
		
		jSpinnerSpeedAir.addChangeListener(this);
		jSpinnerSpeedEarth.addChangeListener(this);
		jSpinnerSpeedWater.addChangeListener(this);

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////// Choix du type de transport en commun ///////////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		jButtonByTrain = new JButton();
		jButtonByTrain.setOpaque(false);
		jButtonByTrain.setIcon(ConfigIcon.getInstance().UNAUTH_TRAIN);
		jButtonByTrain.setContentAreaFilled(false);
		jButtonByTrain.setFocusPainted(false);
		jButtonByTrain.addActionListener(this);
		jButtonByTrain.setMargin(new Insets(0, 0, 0, 0));
		jButtonByTrain.setEnabled(false);

		jButtonByBoat = new JButton();
		jButtonByBoat.setOpaque(false);
		jButtonByBoat.setIcon(ConfigIcon.getInstance().UNAUTH_BOAT);
		jButtonByBoat.setContentAreaFilled(false);
		jButtonByBoat.setFocusPainted(false);
		jButtonByBoat.addActionListener(this);
		jButtonByBoat.setMargin(new Insets(0, 0, 0, 0));
		jButtonByBoat.setEnabled(false);
		
		jButtonByPlane = new JButton();
		jButtonByPlane.setOpaque(false);
		jButtonByPlane.setIcon(ConfigIcon.getInstance().UNAUTH_PLANE);
		jButtonByPlane.setContentAreaFilled(false);
		jButtonByPlane.setFocusPainted(false);
		jButtonByPlane.addActionListener(this);
		jButtonByPlane.setMargin(new Insets(0, 0, 0, 0));
		jButtonByPlane.setEnabled(false);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////// Choix de l'echelle de la map 3D /////////////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		jPanelGPSConfigScaleMap = new PanelGPSConfigScaleMap();
    	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////// Position de la souris ///////////////////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////    	
    	
    	jButtonMousePosition = new JButton();
    	jButtonMousePosition.setOpaque(false);
    	jButtonMousePosition.setIcon(ConfigIcon.getInstance().MOUSE);
    	jButtonMousePosition.setContentAreaFilled(false);
    	jButtonMousePosition.setFocusPainted(false);
    	jButtonMousePosition.setBorderPainted(false);
    	jButtonMousePosition.setMargin(new Insets(0, 0, 0, 0));
    	
    	jLabelPosition1Mouse = new JLabel();
    	jLabelPosition1Mouse.setText("Déplacez la");
    	
    	jLabelPosition2Mouse = new JLabel();
       	jLabelPosition2Mouse.setText("souris sur la carte");
       	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////// Position de depart ////////////////////////////////////////////// 
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
    	
    	jButtonBeginPosition = new JButton();
    	jButtonBeginPosition.setOpaque(false);
    	jButtonBeginPosition.setIcon(ConfigIcon.getInstance().GREEN_FLAG);
    	jButtonBeginPosition.setContentAreaFilled(false);
    	jButtonBeginPosition.setFocusPainted(false);
    	jButtonBeginPosition.addActionListener(this);
    	jButtonBeginPosition.setMargin(new Insets(0, 0, 0, 0));
    	
    	jLabelPosition1Begin = new JLabel();
    	jLabelPosition1Begin.setText("Case non");
    	
    	jLabelPosition2Begin = new JLabel();
       	jLabelPosition2Begin.setText("selectionnée");
    	
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////// Position d'arrivee ////////////////////////////////////////////// 
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	jButtonStopPosition = new JButton();
    	jButtonStopPosition.setOpaque(false);
    	jButtonStopPosition.setIcon(ConfigIcon.getInstance().RED_FLAG);
    	jButtonStopPosition.setContentAreaFilled(false);
    	jButtonStopPosition.setFocusPainted(false);
    	jButtonStopPosition.addActionListener(this);
    	jButtonStopPosition.setMargin(new Insets(0, 0, 0, 0));
    	
    	jLabelPosition1Stop = new JLabel();
    	jLabelPosition1Stop.setText("Case non");
    	
    	jLabelPosition2Stop = new JLabel();
       	jLabelPosition2Stop.setText("selectionnée");
	}



	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent actionEvent)
	{ 
		// TODO recuperer quel composant a ete clique et mettre a jour du modele via le controlleur
		
		// On recupere la source de l'actionEvent
		Object source = actionEvent.getSource();
		
		// Si il s'agit du bouton d'affichage 2D/3D
		if(source == jButtonScaleMap)
		{
			if(jButtonScaleMap.getText().equals("2D"))
			{
				jButtonScaleMap.setIcon(ConfigIcon.getInstance().GPS_3D);
				jButtonScaleMap.setText("3D");
				ctrl.changeTypeMap(true);
			}
			else
			{
				jButtonScaleMap.setIcon(ConfigIcon.getInstance().GPS_2D);
				jButtonScaleMap.setText("2D");
				ctrl.changeTypeMap(false);					
			}
		}
		
		else if(source ==jButtonResult)
		{
			ctrl.calculatePdVWay();
		}
		
		else if (source == jButtonBeginPosition)
		{
			List<String> listString = ctrl.getListCityWithDistrictSortedByName();

			Object[] listCity = new String[listString.size()];		
			for(int i_idity = 0 ; i_idity < listString.size() ; i_idity++)
			{
				listCity[i_idity] = listString.get(i_idity);
			}
			String s = (String)JOptionPane.showInputDialog(
                    ctrl.getView(),
                    "Choix ville départ:\n",
                    "Départ",
                    JOptionPane.PLAIN_MESSAGE,
                    ConfigIcon.getInstance().GREEN_FLAG,
                    listCity,
                    listCity[0]);

			//If a string was returned, say so.
			if ((s != null) && (s.length() > 0)) {
				ctrl.setPolygonBegin(ctrl.getPolygon(s.substring(0, s.indexOf(", "))));
			    return;
			}
		}
		
		else if (source == jButtonStopPosition)
		{
			List<String> listString = ctrl.getListCityWithDistrictSortedByName();
			
			Object[] listCity = new String[listString.size()];		
			for(int i_idity = 0 ; i_idity < listString.size() ; i_idity++)
			{
				listCity[i_idity] = listString.get(i_idity);
			}
			String s = (String)JOptionPane.showInputDialog(
                    ctrl.getView(),
                    "Choix ville arrivée:\n",
                    "Arrivée",
                    JOptionPane.PLAIN_MESSAGE,
                    ConfigIcon.getInstance().RED_FLAG,
                    listCity,
                    listCity[0]);

			//If a string was returned, say so.
			if ((s != null) && (s.length() > 0)) {
				ctrl.setPolygonEnd(ctrl.getPolygon(s.substring(0, s.indexOf(", "))));
			    return;
			}
		}
		
		else if(source == jComboBoxTypeMap)
		{
			ctrl.setI_typeMap3D(jComboBoxTypeMap.getSelectedIndex());
		}
		
		
		else if(source == jButtonByTrain || source == jButtonByBoat || source == jButtonByPlane)
		{
			if(source == jButtonByTrain)
			{
				if(jButtonByTrain.getIcon() == ConfigIcon.getInstance().UNAUTH_TRAIN)
				{
					jButtonByTrain.setIcon(ConfigIcon.getInstance().AUTH_TRAIN);
					ctrl.setHasSelectedTrain(true);
				}
				else
				{
					jButtonByTrain.setIcon(ConfigIcon.getInstance().UNAUTH_TRAIN);
					ctrl.setHasSelectedTrain(false);
				}
			}
			else if(source == jButtonByBoat)
			{
				if(jButtonByBoat.getIcon() == ConfigIcon.getInstance().UNAUTH_BOAT)
				{
					jButtonByBoat.setIcon(ConfigIcon.getInstance().AUTH_BOAT);
					ctrl.setHasSelectedBoat(true);
				}
				else
				{
					jButtonByBoat.setIcon(ConfigIcon.getInstance().UNAUTH_BOAT);
					ctrl.setHasSelectedBoat(false);
				}				
			}
			else if(source == jButtonByPlane)
			{
				if(jButtonByPlane.getIcon() == ConfigIcon.getInstance().UNAUTH_PLANE)
				{
					jButtonByPlane.setIcon(ConfigIcon.getInstance().AUTH_PLANE);
					ctrl.setHasSelectedPlane(true);
				}
				else
				{
					jButtonByPlane.setIcon(ConfigIcon.getInstance().UNAUTH_PLANE);
					ctrl.setHasSelectedPlane(false);
				}				
			}
		}

	}
	
	/**
	 * Renvoie la table contenant toutes les vues
	 */
	public List<I_Viewable> getListI_Viewable()
	{
		return listI_Viewable;
	}
	
	/**
	 * Change la table contenant toutes les vues
	 */
	public void setListI_Viewable(List<I_Viewable> listI_Viewable)
	{
		this.listI_Viewable = listI_Viewable;
		addI_Viewable();
	}

	/**
	 * On ajoute ce panel a la liste de vue
	 */
	public void addI_Viewable()
	{
		listI_Viewable.add(this);
		jPanelGPSConfigScaleMap.setListI_Viewable(listI_Viewable);
	}
	
	/**
	 * On initialise les valeurs de l'IHM
	 */
	public void initValueIHM()
	{
		// TODO
		// Faire un update total du modele
		
	}
	
	/**
	 * Change le controlleur
	 */
	public void setController(Controller ctrl)
	{
		this.ctrl = ctrl;
		jPanelGPSConfigScaleMap.setController(ctrl);
	}

	/**
	 * Renvoie le controlleur
	 */
	public Controller getController()
	{
	   return ctrl;
	}

	@Override
	public void stateChanged(ChangeEvent arg0)
	{

	}

	@Override
	public void typeMapChanged(boolean is3dMap)
	{
		if(is3dMap)
		{
			jComboBoxTypeMap.setVisible(true);
			jLabelTypeMap.setVisible(true);
		}
		else
		{
			jComboBoxTypeMap.setVisible(false);
			jLabelTypeMap.setVisible(false);
		}
	}

	@Override
	public void scaleMapChanged(int i_scale, boolean is3dMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calculDone(float f_nbPdV) {
		if(f_nbPdV > 0)
		{
			jLabelResult.setText(f_nbPdV + " PdV");	
		}
		else
		{
			jLabelResult.setText("Calculer le trajet...");
		}
		
	}

	@Override
	public void newPositionMouse(Polygon polygonMouse) {
		if(polygonMouse == null)
		{
			jLabelPosition1Mouse.setText("Déplacez la");
			jLabelPosition2Mouse.setText("souris sur la carte");
		}
		else
		{
			jLabelPosition1Mouse.setText(polygonMouse.getS_nameDistrict());
			jLabelPosition2Mouse.setText(polygonMouse.getS_nameInDistrict());
		}
	}

	@Override
	public void beginSelected(Polygon polygonBegin) {
		if(polygonBegin == null)
		{
	    	jLabelPosition1Begin.setText("Case non");
	       	jLabelPosition2Begin.setText("selectionnée");
		}
		else
		{
			jLabelPosition1Begin.setText(polygonBegin.getS_nameDistrict());
			jLabelPosition2Begin.setText(polygonBegin.getS_nameInDistrict());
		}
	}

	@Override
	public void stopSelected(Polygon polygonStop) {
		if(polygonStop == null)
		{
	    	jLabelPosition1Stop.setText("Case non");
	       	jLabelPosition2Stop.setText("selectionnée");
		}
		else
		{
			jLabelPosition1Stop.setText(polygonStop.getS_nameDistrict());
			jLabelPosition2Stop.setText(polygonStop.getS_nameInDistrict());
		}
	}

	@Override
	public void searchCriteriaChange() {
		jLabelResult.setText("Calculer le trajet...");
		
	}

	public I_ModelGPSListener getPanelGPSConfigScaleMap() {
		return jPanelGPSConfigScaleMap;
	}

	@Override
	public void changeTypeMap3D(int i_typeMap3D) {
		// Rien a faire
	}

}
