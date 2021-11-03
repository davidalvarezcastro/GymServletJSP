package es.ubu.asi.model;


/**
 * @author david {dac1005@alu.ubu.es}
 */
public class File {
	// attributes
	private long id;
	private String title;
	private String route;
	private long activity; // se podría poner la clase completa, pero por ahora no parece que vaya a hacer falta

	// constructors
	public File() {
		super();
	}

	public File(String title, String route, long activity) {
		super();
		this.title = title;
		this.route = route;
		this.activity = activity;
	}

	public File(long id, String title, String route, long activity) {
		super();
		this.id = id;
		this.title = title;
		this.route = route;
		this.activity = activity;
	}

	// getters & setters
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public long getActivity() {
		return activity;
	}
	public void setActivity(long activity) {
		this.activity = activity;
	}

	// methods
	@Override
    public String toString() {
        return "File{" + "id=" + id + ", title=" + title + ", route=" + route + ", activity=" + activity + '}';
    }
}

