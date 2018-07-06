package config;

import java.util.HashMap;
import java.util.Map;

import reusablecomponents.Utilities;

public class Mappings {
	public static Map<String,String> frenchMappings = new HashMap<String, String>();
	
	/*{{put("Our Services","Nos services");
		put("Customer Support","Soutien");
		put("Drop Off Locations","Lieux de disposition");
		put("About Us","À propos");
		put("My WM","Mon WM");
		put("My Services","Mes services");
		put("My Payment","Mon paiement");
		put("My Preferences","Mes Préférences");
		put("Business","Entreprise");
		put("Residence","Résidence");
		put("Getting Started","Démarrer");
		put("service address","l'adresse du service");
		put("service email","l'adresse courriel du service");
		put("service phone","le numéro de téléphone du service");
		put("customer ID","l'identifiant du client");
		put("Service email","Adresse courriel du service");
		put("Customer ID","L'identifiant du client");
		put("REFER TO PICKUP SCHEDULE","VEUILLEZ VOUS RÉFÉRER AU CALENDRIER DE COLLECTE");
		put("EXTRA PICKUP","AJOUTER COLLECTE");
		put("SERVICE CHANGE","MODIFIER SERVICE");
		put("REPAIR","RÉPARER");
		put("REPORT MISSED PICKUP","COLLECTE MANQUÉE");
		put("Wednesday","MERCREDI");
		put("We’re sorry, but we are unable to provide a service pickup date at this time. Please check back, or contact Customer Support if the problem persists","Nous sommes désolés, mais nous ne sommes pas en mesure de fournir une date de ramassage du service en ce moment. Revenez ou contactez le Service à la clientèle si le problème persiste.");
		//put("Any changes to your regularly scheduled service will be available the week prior to the holiday. Please check back as the holiday approaches to stay informed about potential holiday service changes.","Tout changement à votre calendrier de service normal sera disponible la semaine précédant le jour férié. Veuillez vérifier à nouveau à mesure que la date du jour férié approche afin vous informer sur les éventuels changements de service.");
		put("Any changes to your regularly scheduled service will be available the week prior to the holiday. Please check back as the holiday approaches to stay informed about potential holiday service changes.","Il n'y a pas de jours fériés de prévus dans un proche avenir. Veuillez revenir une semaine avant le prochain jour férié pour vous renseigner sur tout éventuel changement au calendrier de service .");
		put("We found multiple accounts matching this phone number. Please enter your Customer ID for more accurate results.","Plusieurs comptes clients correspondent à ce numéro de téléphone. Veuillez vérifier votre identifiant client pour continuer.");
		put("EMPTY & RETURN","VIDER ET RETOURNER");
		put("REFER TO HOLIDAY SCHEDULE","VEUILLEZ VOUS RÉFÉRER AU CALENDRIER DES JOURS FÉRIÉS");
		put("WHAT CAN I REQUEST?","Requêtes");
		put("NEXT PICKUP","PROCHAINE COLLECTE");
		put("PICKUP SCHEDULE","CALENDRIER");
		put("HOLIDAY SCHEDULE","Jours fériés");
		put("WHAT ELSE CAN I DO?","QUE PUIS-JE FAIRE?");
		put("New Year's Day","Jour de l'An");
		put("Residential: One Day Service Delay Commercial: Up to a Two Day Service Delay Rolloff: Up to a Two Day Service Delay","Résidentiel: délai d'un jour Service commercial: délai de service pouvant aller jusqu'à deux jours: délai d'attente maximal de deux jours");
		put("We could not locate services for this Customer ID. Please try again or use an alternate identification method above.","Nous n'avons pas pu trouver de services pour cet identifiant client. Essayez une autre adresse ou sélectionnez une autre méthode d'identification ci-dessus.");
		put("TODAY","AUJOURD’HUI");
		put("No active services","Aucun service actif");
		put("Your account is temporarily inactive. Please contact (800) 665-1898 for more information.","Votre compte est temporairement inactif. Veuillez composer le (800) 665-1898 pour plus de amples renseignements.");
		put("Your account is inactive. Please contact (800) 665-1898 for more information.","Votre compte est inactif. Veuillez composer le (800) 665-1898 pour de plus amples renseignements.");
		put("We're sorry. Due to the current status on this account, we are unable to provide you with online information at this time. Please contact customer service at (800) 665-1898 for assistance.","Nous sommes désolés. En raison de l'état actuel de ce compte, nous ne sommes pas en mesure de vous fournir des informations en ligne pour le moment. Veuillez communiquer avec le service à la clientèle au (713) 247-1235 pour obtenir de l'aide.");
		put("CUSTOMER SUPPORT","SERVICE À LA CLIENTÈLE");
		put("VISIT BLOG","VISITER LE BLOGUE");
		put("charitable giving","dons de charité");
		put("community initiatives","Initiatives communautaires");
		put("wildlife habitat","habitat faunique");
		put("SEARCH JOBS","Parcourez les emplois");
		put("VIEW BENEFITS","Voyez les avantages");
		put("Pricing and Charges","Prix et frais");
		put("Careers","Carrières");
		put("My WM","Mon WM");
		put("PAP Form","Autorisation des frais");
		put("Site Map","Plan du site");
		put("Privacy","Vie privée");
		put("Safety","Sécurité");
		put("Accessibility","Accessibilité");
		put("We could not locate services for this phone number. Please use an alternate identification method above.","Nous n'avons pas pu trouver de services pour ce numéro de téléphone. Essayez une autre adresse ou sélectionnez une autre méthode d'identification ci-dessus.");
		put("We could not locate services for this email address. Please use an alternate identification method above.","Nous n'avons pas pu trouver de services pour cette adresse courriel. Essayez une autre adresse ou sélectionnez une autre méthode d'identification ci-dessus.");
		put("We found multiple accounts matching this address. Please enter your Customer ID for more accurate results.","Plusieurs comptes clients correspondent à cette adresse. Veuillez vérifier votre identifiant client pour continuer.");
		put("We found multiple accounts matching this email address. Please enter your Customer ID for more accurate results.","Plusieurs comptes clients correspondent à cette adresse courriel. Veuillez vérifier votre identifiant client pour continuer.");
		put("residence","résidence");
		put("business","entreprise");
		put("available products","voir les produits disponibles");
		put("a temporary bin","un conteneur temporaire");
		put("a permanent bin","un conteneur permanent");
		put("paying my bill","payer ma facture");
		put("requesting a pickup","demander une collecte");
		put("recycling services","services de recyclage");
		put("contacting customer service","contacter le service à la clientèle");
		put("my regular and holiday schedules","mon calendrier régulier et de jour fériés");
		put("Learn More","EN SAVOIR PLUS");
		put("learn about us","À propos");
		put("EXPLORE BUSINESS SOLUTIONS","EXPLOREZ");
		put("LEARN MORE","EN SAVOIR PLUS");
		put("See the Story","Lisez l'histoire");
		put("GET ME STARTED","ALLONS-Y");
		put("phone","téléphone");
		put("email","courriel");
		put("Request or change service","Demander un service");
		put("Have a question?","Vous avez une question ?");
		put("Request","Demande");
		put("Change","Changer");
		put("BACK","RETOUR");
		put("Temporary Bin","Conteneur temporaire");
		put("Permanent Bin","Conteneur permanente");
		put("Recycling","Recyclage");
		put("Other","Autre");
		put("Repair or Replacement","Réparation ou remplacement");
		put("Suspend or Resume","Suspension ou Reprise");
		put("Increase or Decrease","Augmenter ou Réduire");
		put("Billing & Payment","Facturation et paiements");
		put("Service","Service");
		put("Technical","Technique");
		put("Technical problems","Problèmes techniques");
		put("Mobile Issues","Problème cellulaire");
		put("Service Day","Jour de collecte");
		put("Missed Pickup","Collecte manquée");
		put("Balance","Solde");
		put("Invoice","Facture");
		put("MY EMPTY & RETURNS","VIDER ET RETOURNER");
		put("RELOCATE","RELOCALISER");
		put("REPAIR ","RÉPARATION");
		put("REMOVE","ENLEVER");
		put("Scheduled","Planifié");
		put("Reviewing Request","Demande de révision");
		put("Unable to Process","Impossible de traiter");
		put("EMPTY & RETURN STATUS","ÉTAT DU SERVICE");
		put("all services","tous les offrons");
		put("Recycling Services","Services de Recyclage");
		put("Temporary Bin","Conteneur Temporaire");
		put("Permanent Bin","Conteneur Permanent");
		put("Please enter a valid email.","Veuillez entrer une acdresse courriel valide.");
		put("* Required","* Requis");
		put("Please enter a valid phone number.","Veuillez entrer un numéro de téléphone valide.");
		put("Your service request has been received and a confirmation email has been sent to the email you provided. We will be in contact with you soon.","Nous avons bien reçu votre demande de service et avons envoyé un courriel de confirmation à l'adresse que vous nous avez fournie. Nous communiquerons avec vous sous peu.");
		put("Thank you for contacting us.","Merci");
		put("Your request is being processed and you will receive a confirmation email shortly. Select ''VIEW ALL EMPTY & RETURNS'' below to edit, cancel, or review the status of your tickets.","Votre demande est en cours de traitement et vous recevrez un courriel de confirmation sous peu. Visitez la page ''État du service Vider et Retourner'' pour modifier, annuler ou passer en revue vos requêtes.");
		put("A Roll off request has been scheduled for this date. If you wish to schedule another, please continue.","Une demande de conteneur amovible a été programmée pour cette date. Si vous souhaitez en planifier une autre, poursuivez.");
		put("customer Id","identifiant du client");
		put("service Phone","numéro de téléphone du service");
		put("service Email ","adresse courriel du service");
	}};*/
	
	public static String getFrenchMapping(String key) throws FrameworkException{
		try {
			String value = frenchMappings.get(key);
			if(value != null) {
				return value;
			}else {
				return key;
			}
			 
		}catch(NullPointerException e) {
			return key;
		}catch(Exception e) {
			throw new FrameworkException("Unknown exception occured while retrieving French Mappings.---" + e.getClass() 
					+ "---" +  e.getMessage());
		}	
	}
	
	public static void setFrenchMappings() throws FrameworkException{
		try {
			String[][] mappings = Utilities.Read_Excel(TestSetup.testDataLocation,
					"FrenchMappings");
			
			for(int i = 0; i<mappings.length;i++) {
				Mappings.frenchMappings.put(mappings[i][1], mappings[i][2]);
			}
			 
		}catch(Exception e) {
			throw new FrameworkException("Unknown exception occured while setting French Mappings.---" + e.getClass() 
					+ "---" +  e.getMessage());
		}	
	}
}
