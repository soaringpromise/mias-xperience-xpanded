$langDir = Join-Path $PSScriptRoot "src/main/resources/assets/xperience-xpanded/lang"
$source = Join-Path $langDir "es_es.json"

$locales = @(
    "es_ar",
    "es_cl",
    "es_ec",
    "es_mx",
    "es_uy",
    "es_ve"
)

if (-not (Test-Path $source)) {
    Write-Error "Source translation file not found: $source"
    exit 1
}

try {
    Get-Content $source -Raw | ConvertFrom-Json -ErrorAction Stop | Out-Null
}
catch {
    Write-Error "Invalid JSON in es_es.json: $_"
    exit 1
}

foreach ($locale in $locales) {
    $destination = Join-Path $langDir "$locale.json"
    Copy-Item $source $destination -Force
    Write-Host "Synced $locale.json"
}

Write-Host "Spanish translations synchronized successfully."