# Initial Layout
Initial implementation of Zoran.io UI

## Design


## Running locally

Navigate to `web` folder:

`$ cd web`

To debug locally with DDC:

```bash
$ pub get
$ pub run build_runner serve
```

To debug locally with Dart2JS, unminified:

```bash
$ pub get
$ pub run build_runner serve --config=debug
```

To debug locally with Dart2JS, minified:

```bash
$ pub get
$ pub run build_runner serve --config=release
```

## Building a binary

```bash
$ pub get
$ pub run build_runner build --config=release -o build
```
