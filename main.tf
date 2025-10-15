resource "aws_s3_bucket" "my_test_bucket" {
  bucket = "my-test-bucket"
}

resource "aws_instance" "ec2_instance" {
  ami           = "ami-0c55b159cbfafe1f0"  # replace with a valid AMI ID for your region
  instance_type = "t2.micro"

  iam_instance_profile = aws_iam_instance_profile.ec2_instance_profile.name

  tags = {
    Name = "test-instance"
  }

  user_data = templatefile("${path.module}/upload_script.sh", {})
}

resource "aws_cloudwatch_log_group" "ec2_log_group" {
  name = "/aws/ec2/test-instance"
}

resource "aws_cloudwatch_log_stream" "ec2_log_stream" {
  name           = "test-log-stream"
  log_group_name = aws_cloudwatch_log_group.ec2_log_group.name
}