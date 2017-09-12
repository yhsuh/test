package org.etri.slice.app;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.etri.slice.commons.Hello;
import org.etri.slice.commons.event.TransactionEvent;

/**
 * A simple Hello service client. This client use annotation instead of XML metadata.
 * If no Hello provider are available, it uses a default implementation.
 *
 * @author <a href="mailto:dev@felix.apache.org">Felix Project Team</a>
 */
@Component//(name = "AnnotatedHelloClient", immediate = true)
@Instantiate
public class HelloClient implements Runnable {

    /**
     * Delay between two invocations.
     */
    private static final int DELAY = 10000;
    /**
     * Hello services. Injected by the container.
     */
    @Requires// (optional = true, defaultimplementation = MyDummyHello.class)
    private Hello hello;
    /**
     * End flag.
     */
    private boolean m_end;
    
    @Property(value = "xxx")
    private String name;
    
    public HelloClient() {
    	System.out.println("HelloClient is constructed.");
    }
    
    /**
     * Run method.
     *
     * @see Runnable#run()
     */
    public void run() {
        while (!m_end) {
            try {
                invokeHelloServices();
                Thread.sleep(DELAY);
            } catch (InterruptedException ie) {
                /* will recheck end */
            }
        }
    }

    /**
     * Invoke hello services.
     */
    public void invokeHelloServices() {
    	TransactionEvent event = new TransactionEvent(Long.valueOf(1), Double.valueOf(100));
    	System.out.println(event);
        System.out.println(hello.sayHello(name));
    }

    /**
     * Starting.
     */
    @Validate
    public void starting() {
    	System.out.println("invalid => valid");
        Thread thread = new Thread(this);
        m_end = false;
//        thread.start();
    }

    /**
     * Stopping.
     */
    @Invalidate
    public void stopping() {
    	System.out.println("valid => invalid");
        m_end = true;
    }
}
