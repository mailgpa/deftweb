package org.deftserver.deftweb.client.tabs.documentation;

import java.util.Arrays;
import java.util.List;

import com.extjs.gxt.charts.client.Chart;
import com.extjs.gxt.charts.client.model.BarDataProvider;
import com.extjs.gxt.charts.client.model.ChartModel;
import com.extjs.gxt.charts.client.model.Legend;
import com.extjs.gxt.charts.client.model.ScaleProvider;
import com.extjs.gxt.charts.client.model.Text;
import com.extjs.gxt.charts.client.model.Legend.Position;
import com.extjs.gxt.charts.client.model.axis.XAxis;
import com.extjs.gxt.charts.client.model.axis.YAxis;
import com.extjs.gxt.charts.client.model.charts.BarChart;
import com.extjs.gxt.charts.client.model.charts.BarChart.BarStyle;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DocumentationTab extends TabItem {

	private final VerticalPanel content = new VerticalPanel();


	public DocumentationTab() {
		super("Documentation");
		content.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		content.setSpacing(10);

		HorizontalPanel chartPanel = new HorizontalPanel();
		chartPanel.setSpacing(20);
		chartPanel.add(getChart("Single instance", "Concurrent requests (keep-alive)"));
		chartPanel.add(getChart("Nginx, four instances", "Concurrent requests (no keep-alive)"));

		content.add(getInitialHtml());
		content.add(chartPanel);
		add(content);
	}

	private Widget getInitialHtml() {
		String html = "<br><br><h3>Overview</h3>" +
				"Deft is an open source web server (licensed under <a href=\"http://www.apache.org/licenses/LICENSE-2.0.html\">Apache version 2</a>). Deft was intitially inspired by <a href=\"http://github.com/facebook/tornado\">facebook/tornado</a>." +
				"<br>Deft is a single threaded, asynchronous, event driven high performance web server running on the JVM." +
				"<br><b>Source and issue tracker:</b> <a href=\"http://github.com/rschildmeijer/deft\">http://github.com/rschildmeijer/deft</a>" + 
				"<br><br><br><h3>Features</h3>" +
				" <i>* Specialized and optimized for thousands of simultaneous connections.</i> (<a href=\"http://en.wikipedia.org/wiki/C10k_problem\">C10k</a>) (<a href=\"http://blog.urbanairship.com/blog/2010/08/24/c500k-in-action-at-urban-airship/\">C500k</a>)" +
				"<br> <i>* Using pure Java NIO</i> (<a href=\"http://download.oracle.com/javase/6/docs/api/java/nio/package-summary.html\">java.nio</a> & <a href=\"http://download.oracle.com/javase/6/docs/api/java/nio/channels/package-summary.html\">java.nio.channels</a>)" +
				"<br> *<i> Asynchronous (nonblocking I/O)</i>" +
				"<br><br><br><h3>Getting started</h3>" +
				"<p><a href=\"http://github.com/downloads/rschildmeijer/deft/deft-0.3.0-binary-with-deps.zip\"> deft-0.3.0-binary-with-deps.zip</a> (recommended)<br>" +
				"<a href=\"http://github.com/downloads/rschildmeijer/deft/deft-0.3.0-binary.zip\">deft-0.3.0-binary.zip</a></p>" +
				"<br><pre><code>    unzip deft-0.3.0-binary-with-deps.zip<br>" +
				"</code></pre><br>" +
				"<h3>Hello world (synchronous)</h3>" +
				"<pre><code>" +
				"    class SynchronousRequestHandler extends RequestHandler {<br><br>" +
				"        &#064;Override<br>" +
				"        public void get(HttpRequest request, HttpResponse response) {<br>" +
                "            response.write(<font color=\"#0000FF\">\"hello world!\"</font>);<br>" +
    			"        }<br>"+
				"    }<br>" +
				"<br>" +
				"    public static void main(String[] args) {<br>" +
				"        Map&#060;String, RequestHandler&#062; handlers = new HashMap&#060;String, RequestHandler&#062;();<br>" +
				"        handlers.put(<font color=\"#0000FF\">\"/\"</font>, new SynchronousRequestHandler());<br>" + 
				"        HttpServer server = new HttpServer(new Application(handlers));<br>" +
				"        server.listen(8080);<br>" +
				"        IOLoop.INSTANCE.start();<br>" +
				"    }<br>" +
				"</pre></code>" +
				"<br><h3>Hello world (asynchronous)</h3>"+ 
				"<pre><code>" +
			    "    class AsynchronousRequestHandler extends RequestHandler {<br><br>" +
			    "        &#064;Override<br>" +
			    "        &#064;Asynchronous<br>" +
			    "        public void get(HttpRequest request, final HttpResponse response) {<br>" +
			    "            response.write(<font color=\"#0000FF\">\"hello \"</font>);<br>" + 
			    "            AsynchronousHttpClient client = new AsynchronousHttpClient();<br>" +
			    "            client.fetch(<font color=\"#0000FF\">\"http://tt.se/start/\"</font>,<br>" +
			    "                new AsyncResult&#060;HttpResponse&#062;() {<br>" +
			    "                    public void onFailure(Throwable ignore) { }<br>" +
			    "                    public void onSuccess(HttpResponse result) { response.write(result.getBody()).finish(); }<br>" +
			    "                }<br>" +
			    "            );<br>" +
			    "        }<br>" +
			    "    }<br><br>" +
			    "    public static void main(String[] args) {<br>" +
			    "        Map&#060;String, RequestHandler&#062; handlers = new HashMap&#060;String, RequestHandler&#062;();<br>" +
			    "        handlers.put(<font color=\"#0000FF\">\"/\"</font>, new AsynchronousRequestHandler());<br>" +
			    "        HttpServer server = new HttpServer(new Application(handlers));<br>" +
			    "        server.listen(8080);<br>" +
			    "        IOLoop.INSTANCE.start();<br>" +
			    "    }<br>" +
			    "</pre></code>" +
			    "By annotating the get method with the <i>org.deftserver.web.Asynchronous</i> annotation you tell Deft that the <br>" +
			    "request is not finished when the get method returns.<br>" +
			    "When the asynchronous http client eventually calls the callback (i.e. onSuccess(HttpResponse result)), <br>" +
			    "the request is still open, and the response is finally flushed to the client with the call to response.finish()." +
			    "<br><br><h3>Capturing groups with regular expressions</h3>" +
			    "<pre><code>" +
			    "    class CapturingRequestHandler extends RequestHandler {<br><br>" +
			    "        &#064;Override<br>" +
			    "        public void get(HttpRequest request, HttpResponse response) {<br>" +
			    "            response.write(request.getRequestedPath());<br>" +
			    "        }<br>" + 
			    "    }<br><br>" +
			    "    public static void main(String[] args) {<br>" +
			    "        Map&#060;String, RequestHandler&#062; handlers = new HashMap&#060;String, RequestHandler&#062;();<br>" +
			    "        handlers.put(<font color=\"#0000FF\">\"/persons/([0-9]+)\"</font>, new CapturingRequestHandler());<br>" +
			    "        HttpServer server = new HttpServer(new Application(handlers));<br>" +
			    "        server.listen(8080);<br>" +
			    "        IOLoop.INSTANCE.start();<br>" +
			    "    }<br>" +
			    "</pre></code>" +
			    "The code above creates a \"dynamic mapping\" to the group capturing request handler (CapturingRequestHandler).<br>" +
			    "This type of mapping is convenient when creating e.g. RESTful web services. The mapping above will \"capture\" all <br>" +
			    "requests made against urls that start with \"/persons/\" and ends with a (positive) number, e.g. \"/persons/1911\"<br> " +
			    "or \"/persons/42\". <br>" +
			    "Capturing groups can only be used as the last url path segment like the example above. <br>" +
			    "It's (currently) not possible to have more than one capturing within one \"dynamic mapping\".<br>" +
			    "<br><br><h3>Logging</h3>" +
			    "All logging in Deft is performed by Logback (SLF4J). Deft ships with a default Logback config file, in which all<br>" +
			    "log-output is written to STDOUT. While this works fine for testing purposes, it is recommended to override this behaviour<br>" +
			    "for \"real world scenarios\". This is done by creating your own logback config file and then specify the location of it<br>" + 
			    "with a system property named <i>logback.configurationFile</i>. The value of this property can be a URL, a resource on the classpath<br>" +
			    "or a path to a file external to the application.<br>"+
			    "Example:<br><br>" +
			    "<pre></code>    java -Dlogback.configurationFile=/path/to/config.xml com.my.DeftServer</pre></code>"+
			    "<br><br><h3>Configuration</h3>"+
			    "All configuration is made through <i>org.deftserver.web.http.HttpServerDescriptor</i>.<br>" +
			    "Do not alter the tunables unless you know what you are doing." +
			    "<br><br><br><h3>Monitoring</h3>"+
			    "Deft release 0.2.0 contains an experimental JMX API (\"<i>The JMX technology provides the tools for building <br>" +
			    "distributed, Web-based, modular and dynamic solutions for managing and monitoring devices, applications, and <br>" +
			    "service-driven networks.</i>\").<br>" +
			    "To monitor the Deft process you would need a JMX client like jconsole or jvisualvm. Both are shipped with modern (Oracle) JDKs." +
			    "<br><br><br><h3>Comparison</h3>"+
				"The following benchmark was done using <a href=\"http://httpd.apache.org/docs/2.0/programs/ab.html\">Apache benchmark</a> (man ab) which is an HTTP server benchmarking tool. " +
				"<br> Deft 0.2.0, Tornado 1.1, node.js 0.2.3, nginx 0.8.52, python 2.6.1, java 1.6.0_22 were used." + 
				"<br>The <a href=\"http://github.com/rschildmeijer/deft-benchmark\">tests</a> (hello world) were executed on a 2.66ghz i7 quad core with 4gb running Mac OS X 10.6.6" +
				"<br>The first chart shows the result when running a single fronted/instance of each server, and for the second chart we used nginx as" +
				"<br>a reverse proxy and loadbalancer, and had four instances of each server running for each test." +
				"<br>Each individual number (bar) is the median of five consecutive runs." +
				"<br>The command executed (for both charts) was:<br><br>" +
				"<pre><code>    ab -k -c[5, 10, 15, 20, 25] -n800000 http://127.0.0.1/</pre></code>" +
				"<br>";
		
		// keywords
		//final String KEYWORDS = "#FF00FF";	//pink
//		final String KEYWORDS = "#7E354D";	//Pale Violet Red4
		final String KEYWORDS = "#810541";	//Maroon
		html = html.replaceAll(" public ", "<font color=\"" + KEYWORDS + "\"> public </font>");
		html = html.replaceAll(" class ", "<font color=\"" + KEYWORDS + "\"> class </font>");
		html = html.replaceAll("static", "<font color=\"" + KEYWORDS + "\">static</font>");
		html = html.replaceAll("void", "<font color=\"" + KEYWORDS + "\">void</font>");
		html = html.replaceAll(" extends ", "<font color=\"" + KEYWORDS + "\"> extends </font>");
		html = html.replaceAll("new", "<font color=\"" + KEYWORDS + "\">new</font>");
		html = html.replaceAll(" final ", "<font color=\"" + KEYWORDS + "\"> final </font>");
		
		// annotations
		final String ANNOTATIONS = "#6E6E6E";	// dark grey
		html = html.replaceAll("&#064;Override", "<font color=\"" + ANNOTATIONS + "\">&#064;Override</font>");
		html = html.replaceAll("&#064;Asynchronous", "<font color=\"" + ANNOTATIONS + "\">&#064;Asynchronous</font>");
		
		// static fields
		final String STATIC_FIELDS = "#0000A0";	// dark blue
		html = html.replaceAll("INSTANCE", "<font color=\"" + STATIC_FIELDS + "\">INSTANCE</font>");
		
		// class names
//		final String CLASS_NAMES = "#CC0000";	// red
//		html = html.replaceAll("String", "<font color=\"" + CLASS_NAMES + "\">String</font>");
//		html = html.replaceAll("-SynchronousRequestHandler-", "<font color=\"" + CLASS_NAMES + "\">SynchronousRequestHandler</font>");
//		html = html.replaceAll("-RequestHandler-", "<font color=\"" + CLASS_NAMES + "\">RequestHandler</font>");
//		html = html.replaceAll("HttpRequest", "<font color=\"" + CLASS_NAMES + "\">HttpRequest</font>");
//		html = html.replaceAll(".HttpResponse.", "<font color=\"" + CLASS_NAMES + "\">HttpResponse</font>");
//		html = html.replaceAll("-Map-", "<font color=\"" + CLASS_NAMES + "\">Map</font>");
//		html = html.replaceAll("-HashMap-", "<font color=\"" + CLASS_NAMES + "\">HashMap</font>");
//		html = html.replaceAll("-HttpServer-", "<font color=\"" + CLASS_NAMES + "\">HttpServer</font>");
//		html = html.replaceAll("Application", "<font color=\"" + CLASS_NAMES + "\">Application</font>");
//		html = html.replaceAll("IOLoop", "<font color=\"" + CLASS_NAMES + "\">IOLoop</font>");
//		html = html.replaceAll("-CapturingRequestHandler-", "<font color=\"" + CLASS_NAMES + "\">CapturingRequestHandler</font>");
//		html = html.replaceAll("AsynchronousRequestHandler", "<font color=\"" + CLASS_NAMES + "\">AsynchronousRequestHandler</font>");
//		html = html.replaceAll("AsynchronousHttpClient", "<font color=\"" + CLASS_NAMES + "\">AsynchronousHttpClient</font>");
//		html = html.replaceAll("AsyncResult", "<font color=\"" + CLASS_NAMES + "\">AsyncResult</font>");
//		html = html.replaceAll("Throwable", "<font color=\"" + CLASS_NAMES + "\">Throwable</font>");

		return new HTML(html);
	}
// nginx 0.8.52
	// python 2.6.1
	private Chart getChart(String titleText, String xLegendText) {
		Chart chart = new Chart("resources/chart/open-flash-chart.swf");
		ListStore<BenchmarkModelData> store = new ListStore<BenchmarkModelData>(); 

		chart.setSize("380", "260");

		ChartModel model = new ChartModel(titleText,  
		"font-size: 14px; font-family: Verdana; text-align: center;");  
		model.setBackgroundColour("#fefefe");  
		model.setLegend(new Legend(Position.TOP, true));  
		model.setScaleProvider(ScaleProvider.ROUNDED_NEAREST_SCALE_PROVIDER);  

		YAxis ya = new YAxis();
		model.setYAxis(ya);

		XAxis xa = new XAxis();
		xa.setLabels("label");
		model.setXAxis(xa);
		model.setXLegend(new Text(xLegendText, "font-size: 12px; font-family: Verdana; text-align: center;"));

		BarChart bar = new BarChart(BarStyle.GLASS);  
		bar.setText("Deft");
		bar.setColour("#00aa00");  
		bar.setTooltip("Deft: #val# requests/sec");
		BarDataProvider barProvider = new BarDataProvider("deft", "c");  
		barProvider.bind(store);
		bar.setDataProvider(barProvider);  
		model.addChartConfig(bar);  

		bar = new BarChart(BarStyle.GLASS);  
		bar.setText("Tornado");
		bar.setColour("#0000cc");  
		bar.setTooltip("Tornado: #val# requests/sec");
		barProvider = new BarDataProvider("tornado");  
		barProvider.bind(store);  
		bar.setDataProvider(barProvider);  
		model.addChartConfig(bar);  

		bar = new BarChart(BarStyle.GLASS);  
		bar.setText("node.js");
		bar.setColour("#ff00cc");  
		bar.setTooltip("node.js: #val# requests/sec");
		barProvider = new BarDataProvider("node.js");  
		barProvider.bind(store);  
		bar.setDataProvider(barProvider);  
		model.addChartConfig(bar); 

		chart.setChartModel(model);  
		store.add("Single instance".equals(titleText) ? getSingleFrontendBenchmarkDataModels() : getNginxBenchmarkDataModels());

		return chart;
	}

	private List<BenchmarkModelData> getSingleFrontendBenchmarkDataModels() {
		return Arrays.asList(
				new BenchmarkModelData[] {
						new BenchmarkModelData(27482, 3059, 10629, 5),
						new BenchmarkModelData(27884, 3142, 11039, 10),
						new BenchmarkModelData(27891, 3194, 11218, 15),
						new BenchmarkModelData(27935, 3220, 11520, 20),
						new BenchmarkModelData(27973, 3234, 11510, 25)
				}
		);
	}

	private List<BenchmarkModelData> getNginxBenchmarkDataModels() {
		return Arrays.asList(
				new BenchmarkModelData[] {
						new BenchmarkModelData(9284, 5578, 9304, 5),
						new BenchmarkModelData(11955, 6143, 9226, 10),
						new BenchmarkModelData(13336, 6393, 9314, 15),
						new BenchmarkModelData(13845, 6563, 9637, 20),
						new BenchmarkModelData(13730, 6664, 9713, 25)
				}
		);
	}

}
