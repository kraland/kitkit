package controller;

import ihm.ConfigIcon;
import ihm.I_ViewDialog;
import ihm.View;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import model.I_ModelDialog;
import model.Model;

public class ModelDownloader extends SwingWorker<Void, Void> implements I_ControllerDialog,
																		I_ViewDialog,
																		I_ModelDialog
	{

	/**
	 * Controleur de l'application
	 */
	private Controller ctrl;
	
	/**
	 * Vue de l'application
	 */
	private View view;
	
	/**
	 * Modele de l'application
	 */
	private Model model;
	
	/**
	 * Booleen indiquant si on souhaite mettre a jour le modèle
	 */
	private boolean hasToUpdateModel;
	
	
	/**
	 * Constructeur
	 * @param hasToUpdateModel
	 */
	public ModelDownloader(boolean hasToUpdateModel)
	{
		this.hasToUpdateModel = hasToUpdateModel;
	}
	
	@Override
	protected Void doInBackground() throws Exception
	{
		// On change le curseur
		view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		if(hasToUpdateModel)
		{
			// On essaye de mettre a jour le modele
			tryToUpdateModel();
		}
		else
		{
			// On essaye de charger un ancien modele
			tryToLoadOldModel();
		}
		
		return null;
	}

    @Override
    public void done()
    {
        // On fait un petit bip
    	Toolkit.getDefaultToolkit().beep();
        
        // On cache la barre de progression
        view.getPanelUpdateModel().changeContentPanelDownload(false);
        
        // On remet le curseur de base
        view.setCursor(null);
    }
    
    /**
	 * Essaye de mettre a jour le modele
	 */
	public void tryToLoadOldModel()
	{	
		// Nom du fichier contenant le timestamp du dernier modele mis a jour
		String s_nameInternetDirectory = "http://kitkit.ki.free.fr/confContentBatiment/";
		
		String s_nameFolder = "fileConf";
		String s_nameFileConf = "confContentBatiment";
		String s_nameFileLast = "list";		
		
		String s_zipExtension = ".zip";
		String s_xmlExtension = ".xml";		
		
		// Buffer permettant de recuperer le contenu de la page 
		StringBuffer buffer = new StringBuffer();
		
		// OptionPane indiquant a l'utilisateur ce que le programme va effectuer
		JOptionPane.showMessageDialog(getView(),"L'application va récuperer le contenu du fichier :\n" + s_nameInternetDirectory + s_nameFileLast + "\n");
		
		// On va recuperer le contenu du fichier list sur le site internet du KITKIT.
		// Ce fichier contient chaque timestamp de chaque modele.
		// Chaque timestamp étant separe par un point-virgule
		buffer = convertContentUrlToStringBuffer(s_nameInternetDirectory + s_nameFileLast);
			
		try
		{
			String tab_s_timestamp[] = buffer.toString().split(";");
			
			if(tab_s_timestamp.length >= 1)
			{
				
				long tab_l_timestamp[] = new long[tab_s_timestamp.length];
				String tab_s_timestampFormatted[] = new String[tab_s_timestamp.length];
				
				// On retranscrit le fichier list en un tableau de long
				for(int i_idTimestamp = 0 ; i_idTimestamp < tab_s_timestamp.length ; i_idTimestamp++)
				{
					tab_l_timestamp[i_idTimestamp] = Long.parseLong(tab_s_timestamp[i_idTimestamp]);
				}
				
				// On cree le formatteur de date
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
							
				// On reformate les timestamp
				for(int i_idTimestamp = 0 ; i_idTimestamp < tab_s_timestamp.length ; i_idTimestamp++)
				{
					Date d = new Date(tab_l_timestamp[i_idTimestamp]);
					tab_s_timestampFormatted[i_idTimestamp] = formatter.format(d);
				}
						
				@SuppressWarnings("static-access")
				String s = (String)JOptionPane.showInputDialog(
	                    ctrl.getView(),
	                    "Modèle du :",
	                    "Choix du modèle à télécharger",
	                    JOptionPane.PLAIN_MESSAGE,
	                    ConfigIcon.getInstance().XML,
	                    tab_s_timestampFormatted,
	                    tab_s_timestampFormatted[0]);
	
				//If a string was returned, say so.
				if ((s != null) && (s.length() > 0))
				{
					// Timestamp chois
					long l_timestampChoosed = 0;
					
					// On parcourt la liste pour savoir quel timestamp a ete choisi
					for(int i_idTimestamp = 0 ; i_idTimestamp < tab_s_timestampFormatted.length ; i_idTimestamp++)
					{
						// Des qu'on trouve le bon timestamp
						if(tab_s_timestampFormatted[i_idTimestamp].equals(s))
						{
							l_timestampChoosed = tab_l_timestamp[i_idTimestamp];
							break;
						}
					}
					
					// On affiche la barre de progression
					view.getPanelUpdateModel().changeContentPanelDownload(true);
						
				   	//On cree le dossier si il n'existe pas encore
				   	File folder = new File(s_nameFolder);
				   	if(!folder.exists()){
				   		folder.mkdir();
				   	}
				    					   	
					// On telecharge le fichier xml du dernier modele
					if(downloadFile(s_nameInternetDirectory + l_timestampChoosed + "_" + s_nameFileConf + s_zipExtension, new File(s_nameFolder + File.separator + s_nameFileConf + s_zipExtension)))
					{
						byte[] tab_byte = new byte[1024];
						try
						{					 
					    	//get the zip file content
						    ZipInputStream zis = new ZipInputStream(new FileInputStream(s_nameFolder + File.separator + s_nameFileConf + s_zipExtension));
						    //get the zipped file list entry
						    ZipEntry ze = zis.getNextEntry();
						   	
						    while(ze!=null)
						    {
						    	// TODO : Normalement cela permet d'extraire tous les fichiers au sein du zip.
						    	// Necessite de verifier
						    	// Car lorsque le fichier des valeurs indicatives ou des organisations sera ajoutée à l'archive il sera necessaire de dezipper ces fichiers
						    	
						   		String fileName = ze.getName();
						        File newFile = new File(s_nameFolder + File.separator + fileName);
						 		System.out.println("Fichier dezippe : "+ newFile.getAbsoluteFile());
						 		
						 		//create all non exists folders
						        //else you will hit FileNotFoundException for compressed folder
						        new File(newFile.getParent()).mkdirs();
						 		FileOutputStream fos = new FileOutputStream(newFile);             
						 		
						 		int len;
						 		while ((len = zis.read(tab_byte)) > 0) {
						 			fos.write(tab_byte, 0, len);
						        }
						 		fos.close();   
						        ze = zis.getNextEntry();
						    }
						 	
						   	zis.closeEntry();
						   	zis.close();
						   	
							System.out.println("Desarchivage termine");
							JOptionPane.showMessageDialog(getView(),"Le modèle est à jour.\nL'application va recharger le modèle");
						    	
							ctrl.loadFileConf(s_nameFolder + File.separator + s_nameFileConf + s_xmlExtension);
						}
						catch(IOException ex){
							ex.printStackTrace();
							JOptionPane.showMessageDialog(getView(),"Erreur lors du dézippage du fichier.","Erreur",JOptionPane.ERROR_MESSAGE);
						}
					}
					else
					{
						JOptionPane.showMessageDialog(getView(),"Impossible de récupèrer le fichier :\n" + s_nameInternetDirectory + s_nameFileConf + s_zipExtension + "\nVérifiez que vous êtes connecté à Internet ou que ce fichier existe.","Erreur",JOptionPane.ERROR_MESSAGE);						
					}
				}
			}
			else
			{
				JOptionPane.showMessageDialog(getView(),"Le contenu du fichier : " + s_nameInternetDirectory + s_nameFileLast + "\nn'est pas au format attendu (" + buffer.toString() +").","Erreur",JOptionPane.ERROR_MESSAGE);
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(getView(),"Le contenu du fichier : " + s_nameInternetDirectory + s_nameFileLast + "\nn'est pas au format attendu (" + buffer.toString() +").","Erreur",JOptionPane.ERROR_MESSAGE);
		}
		
		// On cache la barre de progression
		view.getPanelUpdateModel().changeContentPanelDownload(false);
	}
    
    /**
	 * Essaye de mettre a jour le modele
	 */
	public void tryToUpdateModel()
	{	
		// Nom du fichier contenant le timestamp du dernier modele mis a jour
		String s_nameInternetDirectory = "http://kitkit.ki.free.fr/confContentBatiment/";
		
		String s_nameFolder = "fileConf";
		String s_nameFileConf = "confContentBatiment";
		String s_nameFileLast = "last";		
		
		String s_zipExtension = ".zip";
		String s_xmlExtension = ".xml";		
		
		// Buffer permettant de recuperer le contenu de la page 
		StringBuffer buffer = new StringBuffer();
		
		// OptionPane indiquant a l'utilisateur ce que le programme va effectuer
		JOptionPane.showMessageDialog(getView(),"L'application va récuperer le contenu du fichier :\n" + s_nameInternetDirectory + s_nameFileLast + "\n");
		
		// On va recuperer le contenu du fichier last sur le site internet du KITKIT.
		// Ce fichier contient uniquement le timestamp du dernier modele.
		// Fichier de 13 octets...
		buffer = convertContentUrlToStringBuffer(s_nameInternetDirectory + s_nameFileLast);
			
		try
		{
			// On convertit le buffer du fichier last en long
			long timestamp = Long.parseLong(buffer.toString());
			
			// Si le dernier modele est plus recent
			if(timestamp > getModel().getL_timestampUpdate())
			{
				// On l'indique a l'utilisateur et on lui demande s'il veut le mettre a jour
				int i_response = JOptionPane.showConfirmDialog(getView(),"Un modèle plus récent est disponible.\nVoulez-vous le mettre à jour ?","Question",JOptionPane.YES_NO_OPTION);

				// Si l'utilisateur accepte
				if(i_response == 0)
				{
					// On affiche la barre de progression
					view.getPanelUpdateModel().changeContentPanelDownload(true);
					
				   	//On cree le dossier si il n'existe pas encore
			    	File folder = new File(s_nameFolder);
			    	if(!folder.exists()){
			    		folder.mkdir();
			    	}
			    	
					// On telecharge le fichier xml du dernier modele
					if(downloadFile(s_nameInternetDirectory + s_nameFileConf + s_zipExtension, new File(s_nameFolder + File.separator + s_nameFileConf + s_zipExtension)))
					{
						byte[] tab_byte = new byte[1024];
						try
						{					 
					    	//get the zip file content
						    ZipInputStream zis = new ZipInputStream(new FileInputStream(s_nameFolder + File.separator + s_nameFileConf + s_zipExtension));
						    //get the zipped file list entry
						    ZipEntry ze = zis.getNextEntry();
						   	
						    while(ze!=null)
						    {
						    	// TODO : Normalement cela permet d'extraire tous les fichiers au sein du zip.
						    	// Necessite de verifier
						    	// Car lorsque le fichier des valeurs indicatives ou des organisations sera ajoutée à l'archive il sera necessaire de dezipper ces fichiers
						    	
						   		String fileName = ze.getName();
						        File newFile = new File(s_nameFolder + File.separator + fileName);
						 		System.out.println("Fichier dezippe : "+ newFile.getAbsoluteFile());
						 		
						 		//create all non exists folders
						        //else you will hit FileNotFoundException for compressed folder
						        new File(newFile.getParent()).mkdirs();
						 		FileOutputStream fos = new FileOutputStream(newFile);             
						 		
						 		int len;
						 		while ((len = zis.read(tab_byte)) > 0) {
						 			fos.write(tab_byte, 0, len);
						        }
						 		fos.close();   
						        ze = zis.getNextEntry();
						    }
						 	
						   	zis.closeEntry();
						   	zis.close();
						   	
							System.out.println("Desarchivage termine");
							JOptionPane.showMessageDialog(getView(),"Le modèle est à jour.\nL'application va recharger le modèle");
						    	
							ctrl.loadFileConf(s_nameFolder + File.separator + s_nameFileConf + s_xmlExtension);

						}
						catch(IOException ex){
							ex.printStackTrace();
							JOptionPane.showMessageDialog(getView(),"Erreur lors du dézippage du fichier.","Erreur",JOptionPane.ERROR_MESSAGE);
						}
					}
					else
					{
						JOptionPane.showMessageDialog(getView(),"Impossible de récupèrer le fichier :\n" + s_nameInternetDirectory + s_nameFileConf + s_zipExtension + "\nVérifiez que vous êtes connecté à Internet ou que ce fichier existe.","Erreur",JOptionPane.ERROR_MESSAGE);						
					}
				}
			}
			else
			{
				JOptionPane.showMessageDialog(getView(),"Le modèle est à jour.");
			}
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(getView(),"Le contenu du fichier : " + s_nameInternetDirectory + s_nameFileLast + "\nn'est pas au format attendu (" + buffer.toString() +").","Erreur",JOptionPane.ERROR_MESSAGE);
		}
		
		// On cache la barre de progression
		view.getPanelUpdateModel().changeContentPanelDownload(false);

	}
	
	/**
	 * Telecharge un fichier a l'adresse placee en parametre pour l'enregistrer dans le fichier place en parametre
	 * @param adresse
	 * @param dest
	 * @return
	 */
	private boolean downloadFile(String adresse, File dest)
	{
		// TODO Augmenter les commentaires pour gagner en lisibilite
		
		// Initialisation
		BufferedReader reader = null;
		FileOutputStream fos = null;
		InputStream in = null;
		
		try
		{
			// Creation de la connection
			URL url = new URL(adresse);
			URLConnection conn = url.openConnection();
				
			// On recupere la longueur du fichier
			final int i_fileLenght = conn.getContentLength();
			if (i_fileLenght == -1)
			{
				throw new IOException("Fichier non valide.");
			}	
			
			view.getPanelUpdateModel().updateMaximumProgressBar(i_fileLenght);
			
			// Lecture de la reponse
			in = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in));
			
			// Si le fichier de destination n'existe pas on le cree
			if (dest == null)
			{
				String FileName = url.getFile();
				FileName = FileName.substring(FileName.lastIndexOf('/') + 1);
				dest = new File(FileName);
			}

			fos = new FileOutputStream(dest);
			byte[] buff = new byte[1024];
			int l = in.read(buff);	
			int i_fileLenghtRead = 0;
			// Tant qu'on a pas fini de lire le fichier
			while (l > 0)
			{
				// On ecrit les donnees
				fos.write(buff, 0, l);
				
				// On lit
				l = in.read(buff);
				i_fileLenghtRead+=l;
				view.getPanelUpdateModel().updateValueProgressBar(i_fileLenghtRead);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{
				fos.flush();
				fos.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return false;
			}
			try 
			{
				reader.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return true;
	} 
	
	/**
	 * Recupere le contenu de l'URL placee en parametre et le met dans un StringBuffer
	 * @param s_nameUrl
	 * @return
	 */
	public StringBuffer convertContentUrlToStringBuffer(String s_nameUrl)
	{
		StringBuffer buffer = new StringBuffer();

		try
		{
			boolean isFirstLine= true;
			URL url = new URL(s_nameUrl);
			URLConnection urlConnection = url.openConnection();

			InputStream ips = urlConnection.getInputStream();
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line = null;
			while ((line = br.readLine()) != null)
			{
				if(!isFirstLine)
				{
					buffer.append('\n');
				}
				buffer.append(line);
				
				isFirstLine = false;
			}
			ips.close();
			ipsr.close();
			br.close();
			
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(getView(),"Impossible de récupérer le contenu du fichier :\n" + s_nameUrl + "\nVérifiez que vous êtes connecté à Internet.","Erreur",JOptionPane.ERROR_MESSAGE);

		} 
		
		return buffer;
	}

	@Override
	public void setController(Controller ctrl) {
		this.ctrl = ctrl;
	}

	@Override
	public Controller getController() {
		return ctrl;
	}

	@Override
	public void setView(View view) {
		this.view = view;
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public Model getModel() {
		return model;
	}
}
