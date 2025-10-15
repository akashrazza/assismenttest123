#!/bin/bash
sudo yum update -y
sudo yum install -y python3
pip3 install boto3

echo 'Hello, LocalStack!' > /home/ec2-user/test.txt

python3 << 'PYTHON_SCRIPT'
import boto3
from botocore.exceptions import NoCredentialsError, ClientError

bucket_name = 'my-test-bucket'
file_name = '/home/ec2-user/test.txt'
object_name = 'test.txt'

# Create an S3 client
s3 = boto3.client('s3', endpoint_url='http://localhost:4566')

# Attempt to upload the file
try:
    s3.upload_file(file_name, bucket_name, object_name)
    print(f"File {file_name} uploaded to {bucket_name}/{object_name}")
except NoCredentialsError:
    print("Credentials not available")
except ClientError as e:
    print(f"ClientError: {e}")
PYTHON_SCRIPT