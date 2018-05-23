#!/bin/bash -

# TODO: check for TAG
if [ -z "${TAG}" ]; then
    echo "TAG must be set"
    exit 1
fi

git checkout $(git rev-parse HEAD)
mvn versions:set "-DnewVersion=${TAG}"
sed "s/0.0.0-SNAPSHOT/${TAG}/g" ./README.md > /tmp/README.md
mv /tmp/README.md ./README.md
mvn clean deploy -Pextras,ossrh
git commit -am "[release] puzzlerbot-${TAG}"
git tag -f ${TAG}
git push origin ${TAG}

