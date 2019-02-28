import 'dart:html';

import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_router/angular_router.dart';
import 'package:zoran.io/routing/route_paths.dart';
import 'package:zoran.io/services/pipeline_service.dart';

@Component(
  selector: 'task_viewer',
  styleUrls: const ['task_viewer.scss.css'],
  templateUrl: 'task_viewer.html',
  directives: const [
    coreDirectives,
    MaterialProgressComponent,
    MaterialButtonComponent
  ],
  providers: const
  [
    materialProviders
  ],
)
class TaskViewer implements OnActivate {

  final PipelineService service;

  TaskViewer(this.service);

  void checkState() async {
    this.service.currentTask = await service.check(service.currentTask.id);
  }

  void download() async {
    String b = await service.download(service.currentTask.definition.resourceId);
    AnchorElement link = new AnchorElement()
      ..href = b
      ..text = 'Download Now!';
    var p = querySelector('#text');
    p.append(link);
  }

  @override
  void onActivate(RouterState previous, RouterState current) {
    String uri = getUrl(current.parameters);
    service.check(uri);
  }
}