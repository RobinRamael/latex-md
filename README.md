# latex-md

Markdown -> LaTeX. Very alpha.

## Usage

    lein run <filename>

    or

    lein uberjar
    java -jar latex-md-1.0.0-SNAPSHOT-standalone.jar <filename>

will generate latex code from the markdown in `<filename>`


Only headers, codelistings (not inline) and quotes are supported at the moment.

## License

Copyright (C) 2011 Robin Ramael

Distributed under the GPL License.
