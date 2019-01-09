import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_forms/angular_forms.dart';
import 'package:angular_router/angular_router.dart';
import 'package:sheets_dashboard/routing/route_paths.dart';
import 'package:sheets_dashboard/services/markdown-viewer/markdown_viewer_component.dart';
import 'package:sheets_dashboard/zoran_service.dart';

@Component(
  selector: 'resource-view',
  styleUrls: const ['resource_view_component.scss.css'],
  templateUrl: 'resource_view_component.html',
  directives: const [
    coreDirectives,
    formDirectives,
    materialInputDirectives,
    MaterialIconComponent,
    MaterialButtonComponent,
    MaterialCheckboxComponent,
    MaterialDropdownSelectComponent,
    materialInputDirectives,
    MaterialSpinnerComponent,
    MarkdownViewerComponent
  ]
)
class ResourceViewComponent implements OnActivate , AfterViewInit{

  @Input()
  ProjectDetails details;
  bool disabled = false;

  @ViewChild(MarkdownViewerComponent)
  MarkdownViewerComponent viewer;

  final ZoranService _service;
  ResourceViewComponent(this._service);

  @override
  void onActivate(RouterState previous, RouterState current) async {
    print(details);
    if(details == null) {
      String uri = getUrl(current.parameters);
      details = await _service.getResourceByItsId(uri);
      disabled = true;
    }
    else {
      disabled = false;
    }
  }

  void parseMD() {
    if(viewer != null) {
      viewer.renderMarkdown();
    }
  }

  @override
  void ngAfterViewInit() {
    parseMD();
  }
}