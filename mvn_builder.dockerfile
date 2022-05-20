FROM maven:3-openjdk-8
RUN apt-get update && apt-get install -y ca-certificates curl gnupg lsb-release && apt-get clean
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
RUN echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian \
    $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list
RUN apt-get update && apt-get install -y docker-ce-cli && apt-get clean
VOLUME [ "/var/run/docker.sock" ]