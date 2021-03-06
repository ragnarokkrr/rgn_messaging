<map version="freeplane 1.2.0">
<!--To view this file, download free mind mapping software Freeplane from http://freeplane.sourceforge.net -->
<node TEXT="Apache Camel" LOCALIZED_STYLE_REF="defaultstyle.details" ID="ID_1723255651" CREATED="1283093380553" MODIFIED="1381795339216"><hook NAME="MapStyle" background="#ffffff">

<map_styles>
<stylenode LOCALIZED_TEXT="styles.root_node">
<stylenode LOCALIZED_TEXT="styles.predefined" POSITION="right">
<stylenode LOCALIZED_TEXT="default" MAX_WIDTH="600" COLOR="#000000" STYLE="as_parent">
<font NAME="SansSerif" SIZE="12" BOLD="false" ITALIC="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.details"/>
<stylenode LOCALIZED_TEXT="defaultstyle.note"/>
<stylenode LOCALIZED_TEXT="defaultstyle.floating">
<edge STYLE="hide_edge"/>
<cloud COLOR="#f0f0f0" SHAPE="ROUND_RECT"/>
</stylenode>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.user-defined" POSITION="right">
<stylenode LOCALIZED_TEXT="styles.topic" COLOR="#18898b" STYLE="fork">
<font NAME="Liberation Sans" SIZE="12" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.subtopic" COLOR="#cc3300" STYLE="fork">
<font NAME="Liberation Sans" SIZE="12" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.subsubtopic" COLOR="#669900">
<font NAME="Liberation Sans" SIZE="12" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.important">
<icon BUILTIN="yes"/>
</stylenode>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.AutomaticLayout" POSITION="right">
<stylenode LOCALIZED_TEXT="AutomaticLayout.level.root" COLOR="#000000">
<font SIZE="20"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,1" COLOR="#0033ff">
<font SIZE="18"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,2" COLOR="#00b439">
<font SIZE="16"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,3" COLOR="#990000">
<font SIZE="14"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,4" COLOR="#111111">
<font SIZE="12"/>
</stylenode>
</stylenode>
</stylenode>
</map_styles>
</hook>
<hook NAME="AutomaticEdgeColor" COUNTER="8"/>
<hook NAME="accessories/plugins/AutomaticLayout.properties" VALUE="ALL"/>
<node TEXT="Features" POSITION="right" ID="ID_86021633" CREATED="1381795097182" MODIFIED="1381795100529">
<edge COLOR="#0000ff"/>
<node TEXT="Routing and Mediation Engine" ID="ID_1673523713" CREATED="1381795348290" MODIFIED="1381795355401"/>
<node TEXT="Domain-specific language (DSL)" ID="ID_1336849737" CREATED="1381795355691" MODIFIED="1381795366815"/>
<node TEXT="Payload-agnostic router" ID="ID_1801292569" CREATED="1381795367069" MODIFIED="1381795376841"/>
<node TEXT="POJO model" ID="ID_1281308394" CREATED="1381795377158" MODIFIED="1381795381992"/>
<node TEXT="Automatic type converters" ID="ID_382912914" CREATED="1381795382217" MODIFIED="1381795390228"/>
<node TEXT="Test Kit" ID="ID_30174650" CREATED="1381795390459" MODIFIED="1381795393533"/>
<node TEXT="Enterprise Integration Patterns" ID="ID_913136263" CREATED="1381795393837" MODIFIED="1381795404855"/>
<node TEXT="Extensive component library" ID="ID_1875142372" CREATED="1381795405877" MODIFIED="1381795416111"/>
<node TEXT="Modular and Pluggable architecture" ID="ID_594258469" CREATED="1381795416364" MODIFIED="1381795427944"/>
<node TEXT="Easy configuration" ID="ID_1437222507" CREATED="1381795428174" MODIFIED="1381795433581"/>
<node TEXT="Lightweight core" ID="ID_127492667" CREATED="1381795433783" MODIFIED="1381795440029"/>
<node TEXT="Vibrant Community" ID="ID_1929871483" CREATED="1381795440268" MODIFIED="1381795447014"/>
</node>
<node TEXT="Architecture Overview" POSITION="right" ID="ID_1767262188" CREATED="1381802278210" MODIFIED="1381802287763">
<edge COLOR="#ffff00"/>
<node TEXT="camel_arch.png" ID="ID_1970336485" CREATED="1381802345203" MODIFIED="1381802345203">
<hook URI="camel_arch.png" SIZE="0.9118541" NAME="ExternalObject"/>
</node>
</node>
<node TEXT="Camel Concepts" POSITION="right" ID="ID_1869730307" CREATED="1381803141265" MODIFIED="1381803145458">
<edge COLOR="#7c0000"/>
<node TEXT="CamelContext" ID="ID_1459282371" CREATED="1381803440091" MODIFIED="1381803443717">
<node TEXT="camel_concepts.png" ID="ID_179617656" CREATED="1381803209376" MODIFIED="1381803209376">
<hook URI="camel_concepts.png" SIZE="1.0" NAME="ExternalObject"/>
</node>
<node TEXT="Components" ID="ID_1987707487" CREATED="1381803379213" MODIFIED="1381803382561"/>
<node TEXT="Endpoints" ID="ID_379994708" CREATED="1381803382841" MODIFIED="1381803389084"/>
<node TEXT="Routes" ID="ID_337473773" CREATED="1381803389757" MODIFIED="1381803392117"/>
<node TEXT="Type Converters" ID="ID_70712809" CREATED="1381803392473" MODIFIED="1381803397042"/>
<node TEXT="Data formats" ID="ID_1587369201" CREATED="1381803397306" MODIFIED="1381803400792"/>
<node TEXT="Registry" ID="ID_977551975" CREATED="1381803405917" MODIFIED="1381803411380"/>
<node TEXT="Languages" ID="ID_1635097615" CREATED="1381803412257" MODIFIED="1381803419714"/>
</node>
<node TEXT="Routing Engine" ID="ID_1409570288" CREATED="1381803449051" MODIFIED="1381803452781"/>
<node TEXT="Routes" ID="ID_855470753" CREATED="1381804169262" MODIFIED="1381804171398">
<node TEXT="Decide dynamically what server a client will invoke" ID="ID_801450629" CREATED="1381804173324" MODIFIED="1381804191231"/>
<node TEXT="Provide a flexible way to add extra processing" ID="ID_297504876" CREATED="1381804191496" MODIFIED="1381804204593"/>
<node TEXT="Allow for clients and servers to be developed independently" ID="ID_619758269" CREATED="1381804204838" MODIFIED="1381804223755"/>
<node TEXT="Allow for clients of servers to be stubbed out (using mocks) for testing purposes" ID="ID_1432820840" CREATED="1381804224050" MODIFIED="1381804255032"/>
<node TEXT="Foster better design practices by connecting disparate systems that do one thing well" ID="ID_1672117489" CREATED="1381804259027" MODIFIED="1381804293640"/>
<node TEXT="Enhance features and functionality of some systems (such message brokers and ESBs)" ID="ID_1530961775" CREATED="1381804294676" MODIFIED="1381804318756"/>
</node>
<node TEXT="DSL" ID="ID_80037869" CREATED="1381803460857" MODIFIED="1381803462347"/>
<node TEXT="Processor" ID="ID_1953283752" CREATED="1381803469795" MODIFIED="1381803474519">
<node TEXT="node capable of using, creating, or modifying an incoming message" ID="ID_931480438" CREATED="1381804896038" MODIFIED="1381804947008"/>
</node>
<node TEXT="Component" ID="ID_1628555593" CREATED="1381803475331" MODIFIED="1381803480230">
<node TEXT="main extension point" ID="ID_1982172307" CREATED="1381804851542" MODIFIED="1381804865088">
<node TEXT="data transports" ID="ID_27147252" CREATED="1381804865093" MODIFIED="1381804869030"/>
<node TEXT="dsl&apos;s" ID="ID_290397601" CREATED="1381804870647" MODIFIED="1381804872544"/>
<node TEXT="data formats" ID="ID_1012433247" CREATED="1381804873207" MODIFIED="1381804880952"/>
</node>
</node>
<node TEXT="Endpoint" ID="ID_1218175733" CREATED="1381803486997" MODIFIED="1381803491615">
<node TEXT="camel_endpoint.png" ID="ID_1437281615" CREATED="1381803553393" MODIFIED="1381803553393">
<hook URI="camel_endpoint.png" SIZE="0.99667776" NAME="ExternalObject"/>
</node>
<node TEXT="abstraction for an end of a channel for sending and receiving messages" ID="ID_987924784" CREATED="1381804774820" MODIFIED="1381804834524"/>
</node>
<node TEXT="Producer" ID="ID_652670207" CREATED="1381803576559" MODIFIED="1381803579096">
<node TEXT="camel_producer.png" ID="ID_1978085895" CREATED="1381803629347" MODIFIED="1381803629347">
<hook URI="camel_producer.png" SIZE="1.0" NAME="ExternalObject"/>
</node>
<node TEXT="entity capable of creating and sending a message to an endpoint" ID="ID_30024035" CREATED="1381804464573" MODIFIED="1381804494934">
<node TEXT="FileProducer" ID="ID_875978885" CREATED="1381804494939" MODIFIED="1381804499785"/>
<node TEXT="JmsProducer" ID="ID_2906831" CREATED="1381804500074" MODIFIED="1381804505760"/>
</node>
</node>
<node TEXT="Consumer" ID="ID_59381994" CREATED="1381803647466" MODIFIED="1381803650285">
<node TEXT="service that receives messages from a producer in an exchange" ID="ID_1659584717" CREATED="1381804520297" MODIFIED="1381804578516"/>
</node>
<node TEXT="Event-Driven Consumer" ID="ID_1269382923" CREATED="1381803651042" MODIFIED="1381803656684">
<node TEXT="camel_event_consumer.png" ID="ID_1511385451" CREATED="1381803704014" MODIFIED="1381803704014">
<hook URI="camel_event_consumer.png" SIZE="1.0" NAME="ExternalObject"/>
</node>
<node TEXT="EIP - Asynchronous Receiver" ID="ID_1323053809" CREATED="1381804635624" MODIFIED="1381804650945"/>
</node>
<node TEXT="Polling Consumer" ID="ID_1166367615" CREATED="1381803750013" MODIFIED="1381803755895">
<node TEXT="camel_polling_consumer.png" ID="ID_937522977" CREATED="1381803788649" MODIFIED="1381803788649">
<hook URI="camel_polling_consumer.png" SIZE="1.0" NAME="ExternalObject"/>
</node>
<node TEXT="EIP - Synchronous Receiver" ID="ID_1219515160" CREATED="1381804651977" MODIFIED="1381804675783"/>
</node>
</node>
<node TEXT="Message" POSITION="left" ID="ID_110655917" CREATED="1381795549720" MODIFIED="1381795553171">
<edge COLOR="#00ff00"/>
<node TEXT="Components" ID="ID_1028595589" CREATED="1381795594103" MODIFIED="1381795598539">
<node TEXT="Headers" ID="ID_277670331" CREATED="1381795556993" MODIFIED="1381795562242">
<node TEXT="sender id" ID="ID_13785748" CREATED="1381799452793" MODIFIED="1381799464142"/>
<node TEXT="content encoding" ID="ID_1544181392" CREATED="1381799464411" MODIFIED="1381799472882"/>
<node TEXT="auth data" ID="ID_545047848" CREATED="1381799473142" MODIFIED="1381799478584"/>
</node>
<node TEXT="Attachments" ID="ID_49854826" CREATED="1381795562507" MODIFIED="1381795566681">
<node TEXT="WS" ID="ID_901107613" CREATED="1381799499325" MODIFIED="1381799505022"/>
<node TEXT="E-mail" ID="ID_1675643225" CREATED="1381799505298" MODIFIED="1381799508385"/>
</node>
<node TEXT="Body" ID="ID_1843509251" CREATED="1381795566915" MODIFIED="1381795569890">
<node TEXT="java.lang.Object" ID="ID_935219454" CREATED="1381799516280" MODIFIED="1381799527103"/>
</node>
<node TEXT="Fault Flag" ID="ID_390993284" CREATED="1381799513830" MODIFIED="1381799552917"/>
</node>
<node TEXT="Message in Route" ID="ID_1838180107" CREATED="1381795610649" MODIFIED="1381799415329">
<node TEXT="message_route.png" ID="ID_1169278833" CREATED="1381799395523" MODIFIED="1381799395523">
<hook URI="message_route.png" SIZE="1.0" NAME="ExternalObject"/>
</node>
</node>
<node TEXT="Message Struct" ID="ID_822767950" CREATED="1381799313436" MODIFIED="1381799422833">
<node TEXT="message_struct.png" ID="ID_1936478429" CREATED="1381799328758" MODIFIED="1381799328758">
<hook URI="message_struct.png" SIZE="1.0" NAME="ExternalObject"/>
</node>
</node>
</node>
<node TEXT="Exchange" POSITION="left" ID="ID_424567466" CREATED="1381801814748" MODIFIED="1381801818409">
<edge COLOR="#00ffff"/>
<node TEXT="Exchange Struct" ID="ID_529644056" CREATED="1381801820849" MODIFIED="1381801833164">
<node TEXT="exchange_struct.png" ID="ID_1579418852" CREATED="1381801952592" MODIFIED="1381801952592">
<hook URI="exchange_struct.png" SIZE="1.0" NAME="ExternalObject"/>
</node>
</node>
<node TEXT="Structure" ID="ID_195198044" CREATED="1381801991907" MODIFIED="1381802029097">
<node TEXT="Exchange ID" ID="ID_1077918799" CREATED="1381802092686" MODIFIED="1381802096346"/>
<node TEXT="MEP" ID="ID_827515138" CREATED="1381802029101" MODIFIED="1381802081344">
<node TEXT="InOnly" ID="ID_1626655675" CREATED="1381802049192" MODIFIED="1381802052665"/>
<node TEXT="InOut" ID="ID_723405223" CREATED="1381802053120" MODIFIED="1381802055741"/>
</node>
<node TEXT="Exception" ID="ID_1710024054" CREATED="1381802102525" MODIFIED="1381802107962">
<node TEXT="Exception Hold" ID="ID_942625533" CREATED="1381802126385" MODIFIED="1381802131928"/>
</node>
<node TEXT="Properties" ID="ID_1670683299" CREATED="1381802133434" MODIFIED="1381802136121">
<node TEXT="endure entire exchange" ID="ID_1966045708" CREATED="1381802139905" MODIFIED="1381802160232"/>
<node TEXT="Global info" ID="ID_913757952" CREATED="1381802171699" MODIFIED="1381802178334"/>
</node>
<node TEXT="In Message" ID="ID_1962842064" CREATED="1381802217016" MODIFIED="1381802256677">
<arrowlink SHAPE="CUBIC_CURVE" COLOR="#000000" WIDTH="2" TRANSPARENCY="80" FONT_SIZE="9" FONT_FAMILY="SansSerif" DESTINATION="ID_110655917" STARTINCLINATION="-205;39;" ENDINCLINATION="10;77;" STARTARROW="NONE" ENDARROW="DEFAULT"/>
</node>
<node TEXT="Out Message" ID="ID_1902677439" CREATED="1381802220877" MODIFIED="1381802241290">
<arrowlink SHAPE="CUBIC_CURVE" COLOR="#000000" WIDTH="2" TRANSPARENCY="80" FONT_SIZE="9" FONT_FAMILY="SansSerif" DESTINATION="ID_110655917" STARTINCLINATION="-299;89;" ENDINCLINATION="-23;93;" STARTARROW="NONE" ENDARROW="DEFAULT"/>
</node>
</node>
</node>
<node TEXT="What is Camel?" POSITION="left" ID="ID_1144337571" CREATED="1381895133462" MODIFIED="1381895140445">
<edge COLOR="#00007c"/>
<node TEXT="At the core of the Camel framework is a routing engine, or more precisely a routingengine  builder.  It  allows  you  to  define  your  own  routing  rules,  decide  from  which&#xa;sources to accept messages, and determine how to process and send those messages to&#xa;other destinations. Camel uses an integration language that allows you to define complex routing rules, akin to business processes." ID="ID_1901571272" CREATED="1381895141737" MODIFIED="1381895144895"/>
</node>
</node>
</map>
