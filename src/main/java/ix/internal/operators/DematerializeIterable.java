package ix.internal.operators;

import ix.internal.util.SingleContainer;

import java.util.Iterator;
import java.util.NoSuchElementException;

import rx.Notification;

public final class DematerializeIterable<T> implements Iterable<T> {
	private final Iterable<? extends Notification<? extends T>> source;

	public DematerializeIterable(
			Iterable<? extends Notification<? extends T>> source) {
		this.source = source;
	}

	@Override
	public Iterator<T> iterator() {
	    final Iterator<? extends Notification<? extends T>> it = source.iterator();
	    return new Iterator<T>() {
	        final SingleContainer<Notification<? extends T>> peek = new SingleContainer<Notification<? extends T>>();
	        @Override
	        public boolean hasNext() {
	            if (peek.isEmpty()) {
	                if (it.hasNext()) {
	                    Notification<? extends T> o = it.next();
	                    if (o.isOnCompleted()) {
	                        return false;
	                    }
	                    peek.add(o);
	                }
	            }
	            return !peek.isEmpty();
	        }
	        
	        @Override
	        public T next() {
	            if (hasNext()) {
	                return Interactive.value(peek.take());
	            }
	            throw new NoSuchElementException();
	        }
	        
	        @Override
	        public void remove() {
	            throw new UnsupportedOperationException();
	        }
	        
	    };
	}
}