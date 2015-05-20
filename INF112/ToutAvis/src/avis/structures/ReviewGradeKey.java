package avis.structures;

public class ReviewGradeKey {
    private final MemberKey memberKey;
    private final ReviewKey reviewKey;

    public ReviewGradeKey(MemberKey memberKey, ReviewKey reviewKey) {
        this.memberKey = memberKey;
        this.reviewKey = reviewKey;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReviewGradeKey) {
            ReviewGradeKey key = (ReviewGradeKey) obj;
            return this.memberKey.equals(key.memberKey) && this.reviewKey.equals(key.reviewKey);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + memberKey.hashCode();
        hash = hash * 31 + reviewKey.hashCode();
        return hash;
    }
}
