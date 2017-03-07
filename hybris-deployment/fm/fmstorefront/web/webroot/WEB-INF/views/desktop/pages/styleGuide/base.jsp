<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>

<template:styleGuide pageTitle="Base">

	<jsp:body>

		<div class="container bgwhite">
			<div class="row">
				<div class="col-sm-6 col-sm-push-3">
					<h1><i class="fa fa-cogs" aria-hidden="true"></i> Base Styles</h1>
					<h3><blockquote><i>This section contains all the basic building blocks of the site including colors, fonts, and inline elements</blockquote></h3>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">Headings</h1>
					<h1>Heading 1</h1>
					<p>Paragraph for spacing.</p>
					<h2>Heading 2</h2>
					<p>Paragraph for spacing.</p>
					<h3>Heading 3</h3>
					<p>Paragraph for spacing.</p>
					<h4>Heading 4</h4>
					<p>Paragraph for spacing.</p>
					<h5>Heading 5</h5>
					<p>Paragraph for spacing.</p>
					<h6>Heading 6</h6>
					<p>Paragraph for spacing.</p>


					<h1 class="style-guide-headline">Paragraphs</h1>
					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam non aliquet lorem. Cras id nisi urna. Maecenas tempor leo non urna commodo, et sollicitudin mi dictum. Vivamus et volutpat mi. Donec vestibulum a lorem in tincidunt. Proin sed purus pretium, interdum tortor in, ultricies eros. Morbi ut consectetur magna. Curabitur iaculis vehicula sem, in auctor lectus. Vestibulum sit amet consectetur elit. Donec semper lacus non maximus imperdiet. Maecenas id dictum metus. Vivamus dignissim tempor arcu, ac auctor ipsum vulputate nec.</p>
					<p>Sed lobortis lorem eu efficitur tristique. Mauris vitae velit convallis, posuere leo interdum, euismod nulla. Phasellus semper aliquam porta. Fusce non dapibus ex. Duis et orci et risus sodales blandit a nec metus. Donec malesuada nunc in risus venenatis auctor. Vivamus ac turpis nec enim elementum ultrices. Aenean ut euismod lacus. Curabitur eget interdum lectus, vel vulputate mi. Nullam id venenatis diam, sed fringilla erat. Fusce vel tempus ex, suscipit maximus ligula.</p>


					<h1 class="style-guide-headline">Blockquote</h1>
					<blockquote><i>Blockquote.</i> Sed lobortis lorem eu efficitur tristique. Mauris vitae velit convallis, posuere leo interdum, euismod nulla. Phasellus semper aliquam porta. Fusce non dapibus ex. Duis et orci et risus sodales blandit a nec metus. Donec malesuada nunc in risus venenatis auctor. Vivamus ac turpis nec enim elementum ultrices.</blockquote>


					<h1 class="style-guide-headline">Inline Elements</h1>
					<p><a href="#">This is a text link</a></p>
					<p><strong>Strong is used to indicate strong importance</strong></p>
					<p><em>This text has added emphasis</em></p>
					<p>The <b>b element</b> is stylistically different text from normal text, without any special importance</p>
					<p>The <i>i element</i> is text that is set off from the normal text</p>
					<p>The <u>u element</u> is text with an unarticulated, though explicitly rendered, non-textual annotation</p>
					<p><del>This text is deleted</del> and <ins>This text is inserted</ins></p>
					<p><s>This text has a strikethrough</s></p>
					<p>Superscript<sup>®</sup></p>
					<p>Subscript for things like H<sub>2</sub>O</p>
					<p><small>This small text is small for for fine print, etc.</small></p>
					<p>Abbreviation: <abbr title="HyperText Markup Language">HTML</abbr></p>
					<p>Keybord input: <kbd>Cmd</kbd></p>
					<p><q cite="https://developer.mozilla.org/en-US/docs/HTML/Element/q">This text is a short inline quotation</q></p>
					<p><cite>This is a citation</cite>
					</p><p>The <dfn>dfn element</dfn> indicates a definition.</p>
					<p>The <mark>mark element</mark> indicates a highlight</p>
					<p><code>This is what inline code looks like.</code></p>
					<p><samp>This is sample output from a computer program</samp></p>
					<p>The <var>variarble element</var>, such as <var>x</var> = <var>y</var></p>

					<h1 class="style-guide-headline">Lists</h1>
					<p><b>Unordered List</b></p>
					<ul>
						<li>This is a list item in an unordered list</li>
						<li>An unordered list is a list in which the sequence of items is not important. Sometimes, an unordered list is a bulleted list. And this is a long list item in an unordered list that can wrap onto a new line.</li>
						<li>Lists can be nested inside of each other
							<ul>
								<li>This is a nested list item</li>
								<li>This is another nested list item in an unordered list</li>
							</ul>
						</li>
						<li>This is the last list item</li>
					</ul>

					<p><b>Ordered List</b></p>
					<ol>
						<li>This is a list item in an ordered list</li>
						<li>An ordered list is a list in which the sequence of items is important. An ordered list does not necessarily contain sequence characters.</li>
						<li>Lists can be nested inside of each other
							<ol>
								<li>This is a nested list item</li>
								<li>This is another nested list item in an ordered list</li>
							</ol>
						</li>
						<li>This is the last list item</li>
					</ol>

					<h1 class="style-guide-headline">Horizontal Rule</h1>
					<hr />

				</div>
			</div>
		</div>
	
	</jsp:body>

</template:styleGuide>