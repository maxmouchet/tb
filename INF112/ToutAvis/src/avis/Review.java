package avis;


public class Review {

	/**
	 * @uml.property  name="note"
	 */
	private float note;

	/**
	 * Getter of the property <tt>note</tt>
	 * @return  Returns the note.
	 * @uml.property  name="note"
	 */
	public float getNote() {
		return note;
	}

	/**
	 * Setter of the property <tt>note</tt>
	 * @param note  The note to set.
	 * @uml.property  name="note"
	 */
	public void setNote(float note) {
		this.note = note;
	}

	/**
	 * @uml.property  name="commentaire"
	 */
	private String commentaire;

	/**
	 * Getter of the property <tt>commentaire</tt>
	 * @return  Returns the commentaire.
	 * @uml.property  name="commentaire"
	 */
	public String getCommentaire() {
		return commentaire;
	}

	/**
	 * Setter of the property <tt>commentaire</tt>
	 * @param commentaire  The commentaire to set.
	 * @uml.property  name="commentaire"
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	/**
	 * @uml.property  name="member"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="reviews:avis.Member"
	 */
	private Member member = new avis.Member();

	/**
	 * Getter of the property <tt>member</tt>
	 * @return  Returns the member.
	 * @uml.property  name="member"
	 */
	public Member getMember() {
		return member;
	}

	/**
	 * Setter of the property <tt>member</tt>
	 * @param member  The member to set.
	 * @uml.property  name="member"
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * @uml.property  name="item"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="reviews:avis.Item"
	 */
	private Item item = new avis.Book();

	/**
	 * Getter of the property <tt>item</tt>
	 * @return  Returns the item.
	 * @uml.property  name="item"
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * Setter of the property <tt>item</tt>
	 * @param item  The item to set.
	 * @uml.property  name="item"
	 */
	public void setItem(Item item) {
		this.item = item;
	}

}
