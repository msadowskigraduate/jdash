import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_forms/angular_forms.dart';
import 'package:angular_router/angular_router.dart';
import 'package:zoran.io/routing/route_paths.dart';
import 'package:zoran.io/services/markdown-viewer/markdown_viewer_component.dart';
import 'package:zoran.io/services/resource_service.dart';
import 'package:zoran.io/services/zoran_service.dart';

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
    MarkdownViewerComponent,
  ]
)
class ResourceViewComponent implements OnActivate , AfterViewInit{

  @Input()
  ResourceResponse details;

  @Input('new')
  NewResourceRequest newResource;

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
      disabled = false;
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