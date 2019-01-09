import 'dart:html';

import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:markdown/markdown.dart' as md;

@Component(
  selector: 'markdown',
  styleUrls: const [],
  template: '<div class="markdownviewer"><div id="markdown"></div></div>',
  directives: const [
    coreDirectives
  ],
  providers: const
  [
    materialProviders
  ],
)
class MarkdownViewerComponent {
  @Input()
  String description;

  final nullSanitizer = new NullTreeSanitizer();

  void renderMarkdown() {
    final htmlDiv = document.querySelector('#markdown') as DivElement;
    htmlDiv.setInnerHtml(md.markdownToHtml(description,
        extensionSet: md.ExtensionSet.gitHubWeb),
        treeSanitizer: nullSanitizer);
  }
}

class NullTreeSanitizer implements NodeTreeSanitizer {
  void sanitizeTree(Node node) {}
}