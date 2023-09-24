FROM nginx:1.19.6-alpine

#Remove o arquivo de configuração padrão do nginx
RUN rm /etc/nginx/conf.d/default.conf

#Copia o arquivo criado
COPY nginx.conf /etc/nginx/conf.d/default.conf