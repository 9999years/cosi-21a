public class Equality {
	public static boolean nullableEquals(Object a, Object b) {
		if(a == null) {
			return b == null;
		} else {
			return a.equals(b);
		}
	}
}
