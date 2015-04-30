package avis;

import java.util.LinkedList;

import avis.models.Book;
import avis.models.Film;
import avis.models.Item;
import avis.models.Member;
import avis.models.Review;
import exception.BadEntry;
import exception.ItemBookAlreadyExists;
import exception.ItemFilmAlreadyExists;
import exception.MemberAlreadyExists;
import exception.NotItem;
import exception.NotMember;

/** 
 * @author A. Beugnard, 
 * @author G. Ouvradou
 * @author B. Prou
 * @date février - mars 2011
 * @version V0.6
 */

/** 
 * <p>
 * <b>Système de mutualisation d'opinions portant sur des domaines
 * variés (littérature, cinéma, art, gastronomie, etc.) et non limités.</b>
 * </p>
 * <p>
 * L'accès aux items et aux opinions qui leurs sont associées
 * est public. La création d'item et le dépôt d'opinion nécessite en revanche 
 * que l'utilisateur crée son profil au préalable.
 * </p>
 * <p>
 * Lorsqu'une méthode peut lever deux types d'exception, et que les conditions sont réunies 
 * pour lever l'une et l'autre, rien ne permet de dire laquelle des deux sera effectivement levée.
 * </p>
 * <p>
 * Dans une version avancée (version 2), une opinion peut également
 * être évaluée. Chaque membre se voit dans cette version décerner un "karma" qui mesure
 * la moyenne des notes portant sur les opinions qu'il a émises.
 * L'impact des opinions entrant dans le calcul de la note moyenne attribuée à un item
 * est pondéré par le karma des membres qui les émettent.
 * </p>
 */

public class SocialNetwork {
	
	/**
	 * @uml.property   name="items"
	 * @uml.associationEnd   multiplicity="(0 -1)" aggregation="composite" inverse="socialNetwork:avis.models.Item"
	 */
	private LinkedList<Item> items;

	/**
	 * @uml.property   name="members"
	 * @uml.associationEnd   multiplicity="(0 -1)" aggregation="composite" inverse="socialNetwork:avis.models.Member"
	 */
	private LinkedList<Member>  members;

	/**
	 * constructeur de <i>SocialNetwok</i> 
	 * 
	 */
	public SocialNetwork() {
		this.items = new LinkedList<Item>();
		this.members = new LinkedList<Member>();
	}

	/**
	 * Obtenir le nombre de membres du <i>SocialNetwork</i>
	 * 
	 * @return le nombre de membres
	 */
	public int nbMembers() {
		return members.size();
	}

	/**
	 * Obtenir le nombre de films du <i>SocialNetwork</i>
	 * 
	 * @return le nombre de films
	 */
	public int nbFilms() {
		return countItems(Film.class);
	}

	/**
	 * Obtenir le nombre de livres du <i>SocialNetwork</i>
	 * 
	 * @return le nombre de livres
	 */
	public int nbBooks() {
		return countItems(Book.class);
	}
	
	private int countItems(Class<?> klass) {
		int count = 0;
		
		for (Item item : items) {
			if (klass.isInstance(item)) { count++; }
		}
		
		return count;
	}

	/**
	 * Ajouter un nouveau membre au <i>SocialNetwork</i>
	 * 
	 * @param pseudo son pseudo
	 * @param password son mot de passe 
	 * @param profil un slogan choisi par le membre pour se définir
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instancié ou a moins de 1 caractère autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instancié ou a moins de 4 caractères autres que des leadings or trailing blanks. </li>
	 *  <li>  si le profil n'est pas instancié.  </li>
	 * </ul><br>       
	 * 
	 * @throws MemberAlreadyExists membre de même pseudo déjà présent dans le <i>SocialNetwork</i> (même pseudo : indifférent à  la casse  et aux leadings et trailings blanks)
	 * 
	 */
	public void addMember(String pseudo, String password, String profil) throws BadEntry, MemberAlreadyExists  {
		Member m = new Member(pseudo, password, profil);
		
		for (Member member : members) {
			if (member.getPseudo().trim().toLowerCase().equals(pseudo.trim().toLowerCase())) {
				throw new MemberAlreadyExists();
			}
		}
		
		this.members.add(m);
	}

	/**
	 * Ajouter un nouvel item de film au <i>SocialNetwork</i> 
	 * 
	 * @param pseudo le pseudo du membre
	 * @param password le password du membre 
	 * @param titre le titre du film
	 * @param genre son genre (aventure, policier, etc.)
	 * @param realisateur le réalisateur
	 * @param scenariste le scénariste
	 * @param duree sa durée en minutes
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instancié ou a moins de 1 caractère autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instancié ou a moins de 4 caractères autres que des leadings or trailing blanks. </li>
	 *  <li>  si le titre n'est pas instancié ou a moins de 1 caractère autre que des espaces.  </li>
	 *  <li>  si le genre n'est pas instancié. </li>
	 *  <li>  si le réalisateur n'est pas instancié. </li>
	 *  <li>  si le scénariste n'est pas instancié. </li>
	 *  <li>  si la durée n'est pas positive.  </li>
	 * </ul><br>       
	 * @throws NotMember : si le pseudo n'est pas celui d'un membre ou si le pseudo et le password ne correspondent pas.
	 * @throws ItemFilmAlreadyExists : item film de même titre  déjà présent (même titre : indifférent à  la casse  et aux leadings et trailings blanks)
	 * 
	 */
	public void addItemFilm(String pseudo, String password, String titre, String genre, String realisateur, String scenariste, int duree) throws BadEntry, NotMember, ItemFilmAlreadyExists {
		findMatchingMember(pseudo, password);
		
		Film film = new Film(titre, genre, realisateur, scenariste, duree);
		
		for (Item item : items) {
			if (item.getTitle().trim().toLowerCase().equals(titre.trim().toLowerCase()) && item instanceof Book) {
				throw new ItemFilmAlreadyExists();
			}
		}
		
		this.items.add(film);
	}

	/**
	 * Ajouter un nouvel item de livre au <i>SocialNetwork</i> 
	 * 
	 * @param pseudo le pseudo du membre
	 * @param password le password du membre 
	 * @param titre le titre du livre
	 * @param genre son genre (roman, essai, etc.)
	 * @param auteur l'auteur
	 * @param nbPages le nombre de pages
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instancié ou a moins de 1 caractère autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instancié ou a moins de 4 caractères autres que des leadings or trailing blanks. </li>
	 *  <li>  si le titre n'est pas instancié ou a moins de 1 caractère autre que des espaces.  </li>
	 *  <li>  si le genre n'est pas instancié. </li>
	 *  <li>  si l'auteur n'est pas instancié. </li>
	 *  <li>  si le nombre de pages n'est pas positif.  </li>
	 * </ul><br>       
	 * @throws NotMember : si le pseudo n'est pas celui d'un membre ou si le pseudo et le password ne correspondent pas.
	 * @throws ItemBookAlreadyExists item livre de même titre  déjà présent (même titre : indifférent à la casse  et aux leadings et trailings blanks)
	 * 
	 * 
	 */
	public void addItemBook(String pseudo, String password, String titre, String genre, String auteur, int nbPages) throws  BadEntry, NotMember, ItemBookAlreadyExists{
		findMatchingMember(pseudo, password);
		
		Book book = new Book(titre, genre, auteur, nbPages);
		
		for (Item item : items) {
			if (item.getTitle().trim().toLowerCase().equals(titre.trim().toLowerCase()) && item instanceof Book) {
				throw new ItemBookAlreadyExists();
			}
		}
		
		this.items.add(book);
	}
	
	/**
	 * Consulter les items du <i>SocialNetwork</i> par nom
	 * 
	 * @param nom son nom (eg. titre d'un film, d'un livre, etc.)
	 * 
	 * @throws BadEntry : si le nom n'est pas instancié ou a moins de 1 caractère autre que des espaces.  </li>
	 * 
	 * @return LinkedList <String> : la liste des représentations de tous les items ayant ce nom 
	 * Cette représentation contiendra la note de l'item s'il a été noté.
	 * (une liste vide si aucun item ne correspond) 
	 */
	public LinkedList <String> consultItems(String nom) throws BadEntry {
		// TODO: Check that it shows note.
		if (!Item.titleIsValid(nom)) {
			throw new BadEntry("Title does not meet the requirements.");
		}
		
		LinkedList<String> itemsStrings = new LinkedList<String>();
		
		for (Item item : items) {
			itemsStrings.add(item.toString());
		}
		
		return itemsStrings;
	}

	/**
	 * Donner son opinion sur un item film.
	 * Ajoute l'opinion de ce membre sur ce film au <i>SocialNetwork</i> 
	 * Si une opinion de ce membre sur ce film  préexiste, elle est mise à jour avec ces nouvelles valeurs.
	 * 
	 * @param pseudo pseudo du membre émettant l'opinion
	 * @param password son mot de passe
	 * @param titre titre du film  concerné
	 * @param note la note qu'il donne au film 
	 * @param commentaire ses commentaires
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instancié ou a moins de 1 caractère autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instancié ou a moins de 4 caractères autres que des leadings or trailing blanks. </li>
	 *  <li>  si le titre n'est pas instancié ou a moins de 1 caractère autre que des espaces.  </li>
	 *  <li>  si la note n'est pas comprise entre 0.0 et 5.0. </li>
	 *  <li>  si le commentaire n'est pas instancié. </li>
	 * </ul><br>       
	 * @throws NotMember : si le pseudo n'est pas celui d'un membre ou si le pseudo et le password ne correspondent pas.
	 * @throws NotItem : si le titre n'est pas le titre d'un film.
	 * 
	 * @return la note moyenne des notes sur ce film  
	 */
	public float reviewItemFilm(String pseudo, String password, String titre, float note, String commentaire) throws BadEntry, NotMember, NotItem {
		return reviewItem(Film.class, pseudo, password, titre, note, commentaire);
	}

	/**
	 * Donner son opinion sur un item livre.
	 * Ajoute l'opinion de ce membre sur ce livre au <i>SocialNetwork</i> 
	 * Si une opinion de ce membre sur ce livre  préexiste, elle est mise à jour avec ces nouvelles valeurs.
	 * 
	 * @param pseudo pseudo du membre émettant l'opinion
	 * @param password son mot de passe
	 * @param titre titre du livre  concerné
	 * @param note la note qu'il donne au livre 
	 * @param commentaire ses commentaires
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instancié ou a moins de 1 caractère autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instancié ou a moins de 4 caractères autres que des leadings or trailing blanks. </li>
	 *  <li>  si le titre n'est pas instancié ou a moins de 1 caractère autre que des espaces.  </li>
	 *  <li>  si la note n'est pas comprise entre 0.0 et 5.0. </li>
	 *  <li>  si le commentaire n'est pas instancié. </li>
	 * </ul><br>       
	 * @throws NotMember : si le pseudo n'est pas celui d'un membre ou si le pseudo et le password ne correspondent pas.
	 * @throws NotItem : si le titre n'est pas le titre d'un livre.
	 * 
	 * @return la note moyenne des notes sur ce livre
	 */
	public float reviewItemBook(String pseudo, String password, String titre, float note, String commentaire) throws BadEntry, NotMember, NotItem {
		return reviewItem(Book.class, pseudo, password, titre, note, commentaire);
	}

	private float reviewItem(Class<?> klass, String pseudo, String password, String titre, float note, String commentaire) throws BadEntry, NotMember, NotItem {
		if (!(Member.pseudoIsValid(pseudo) && Member.passwordIsValid(password))) {
			throw new BadEntry("Pseudo and/or password does not meet the requirements.");
		}

		if (!Item.titleIsValid(titre)) {
			throw new BadEntry("Item title does not meet the requirements.");
		}
		
		Item item = findMatchingItem(klass, titre);
		Member member = findMatchingMember(pseudo, password);

		Review review = member.findReview(titre);
		if (review == null) {
			review = new Review(item, member, commentaire, note);
			member.addReview(review);
			item.addReview(review);
		} else {
			review.update(commentaire, note);
		}

		return item.getRating();
	}
	
	private Item findMatchingItem(Class<?> klass, String title) throws NotItem {
		Item item = null;
		
		for (Item it : items) {
			if ((it.getTitle() == title) && (klass.isInstance(it))) {
				item = it;
				break;
			}
		}

		if (item == null) {
			throw new NotItem("Item not found.");
		}
		
		return item;
	}
	
	private Member findMatchingMember(String pseudo, String password) throws NotMember {
		Member member = null;
		
		for (Member m : members){
			if(m.checkCredentials(pseudo, password)){
				member = m;
				break;
			}
		}

		if(member == null) {
			throw new NotMember("Invalid credentials.");
		}
		
		return member;
	}

	/**
	 * Obtenir une représentation textuelle du <i>SocialNetwork</i>.
	 * 
	 * @return la chaîne de caractères représentation textuelle du <i>SocialNetwork</i> 
	 */
	public String toString() {
		String output = "";
		
		output += "SocialNetwork" + "\n";
		output += nbMembers() + " members" + "\n";
		output += nbBooks() + " books" + "\n";
		output += nbFilms() + " films" + "\n";
		
		return output;
	}
}
