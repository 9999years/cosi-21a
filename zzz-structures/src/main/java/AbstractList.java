import java.lang.UnsupportedOperationException;
import java.lang.ClassCastException;
import java.lang.Iterable;
import java.util.NoSuchElementException;

import java.util.*;

import static Preconditions.*;

public abstract class AbstractList<E>
		extends AbstractCollection<E> implements List<E> {
	protected class AbstractListIterator implements ListIterator<E> {
		// index of previous() el
		protected int index = -1;
		// index of last returned el
		protected int last = -1;

		AbstractListIterator() { }

		AbstractListIterator(int index) {
			checkArgument(index >= -1
				&& index <= AbstractList.this.size(),
				"invalid index");
			this.index = index + 1;
			last = this.index;
		}

		public int nextIndex() {
			return index + 1;
		}

		public int previousIndex() {
			return index;
		}

		public boolean hasNext() {
			return index + 1 < size();
		}

		public boolean hasPrevious() {
			return index > 0;
		}

		public E next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			index++;
			last = index;
			return AbstractList.this.get(last);
		}

		public E previous() {
			if(!hasPrevious()) {
				throw new NoSuchElementException();
			}
			last = index;
			index--;
			return AbstractList.this.get(last);
		}

		public void add(E e) {
			AbstractList.this.add(index + 1, e);
		}

		public void set(E e) {
			AbstractList.this.set(last, e);
		}

		public void remove() {
			AbstractList.this.remove(last);
		}
	}

	protected int modCount = 0;

	AbstractList() {}

	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	public void add(int index, E element) {
		throw new UnsupportedOperationException();
	}

	public abstract E get(int index);

	@Override
	public Iterator<E> iterator() {
		return new AbstractListIterator();
	}

	public ListIterator<E> listIterator() {
		return new AbstractListIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return new AbstractListIterator(index);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		for(E e : c) {
			add(index, e);
			index++;
		}
		return true;
	}

	@Override
	public boolean add(E e) {
		add(size(), e);
		return true;
	}

	@Override
	public void clear() {
		removeRange(0, size());
	}

	/**
	 * logic from {@see java.util.List#hashCode()}
	 */
	@Override
	public int hashCode() {
		int hashCode = 1;
		for (E e : this) {
			hashCode = 0b11111 * hashCode + Objects.hashCode(e);
		}
		return hashCode;
	}

	protected void removeRange(int fromIndex, int toIndex) {
		for(int i = fromIndex; i < toIndex; i++) {
			remove(i);
		}
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof List)) {
			return false;
		}

		List<?> list = (List<?>) o;

		Iterator<E> thisItr = iterator();
		Iterator<?> otherItr = list.iterator();

		while(thisItr.hasNext() && otherItr.hasNext()) {
			if(!Objects.equals(thisItr.next(), otherItr.next())) {
				return false;
			}
		}

		return !thisItr.hasNext() && !otherItr.hasNext();
	}

	public int indexOf(Object o) {
		ListIterator<E> itr = listIterator();
		while(itr.hasNext()) {
			if(Objects.equals(o, itr.next())) {
				return itr.previousIndex();
			}
		}
		return -1;
	}
}
